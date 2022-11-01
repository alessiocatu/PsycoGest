package io.utacfreak.psycogest.ui.Panel;

import io.utacfreak.psycogest.back.Statistic.StatisticController;
import io.utacfreak.psycogest.ui.GraphicConst;
import io.utacfreak.psycogest.ui.Components.GenericComponent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StatisticPanel extends JPanel {

    public StatisticPanel(int month){
        StatisticController.getStatisticController().elaborateFattureCSV();

        this.setBackground(GraphicConst.BACKGROUND);
        this.setLayout(new GridLayout(4,3));

        //##########PRIMA RIGA
        //ETA CHART
        this.add(RingGraph("Età", pieDataset(StatisticController.getStatisticController().getEtaDataSet())));
        //TITOLO
        this.add(GenericComponent.H1("Analisi dei tuoi pazienti"));
        //SCELTA PERIODO
        List<String> menu = StatisticController.getStatisticController().getFattureMenuList();
        String[] arr = new String[menu.size()];
        Container box = GenericComponent.ComboBoxWithListener("Periodo ", StatisticController.getStatisticController().getFattureMenuList().toArray(arr), month);
        this.add(box);

        //###########SECONDA RIGA
        //DSA CHART
        this.add(RingGraph("DSA", pieDataset(StatisticController.getStatisticController().getDSADataSet())));
        //FATTURE
        Container fatture = new Container();
        fatture.setLayout(new GridLayout(5,1));
        fatture.add(GenericComponent.TextField("Fatture effettuate ", StatisticController.getStatisticController().getNumeroFatture(menu.get(GenericComponent.isComboBox(box)))));
        double tot = StatisticController.getStatisticController().getTotaleFatture(menu.get(GenericComponent.isComboBox(box)));
        fatture.add(GenericComponent.TextField("Totale Netto ", Double.toString(tot)));
        double iva = StatisticController.getStatisticController().getIvaFatture(menu.get(GenericComponent.isComboBox(box)));
        fatture.add(GenericComponent.TextField("Totale IVA ", Double.toString(iva)));
        fatture.add(GenericComponent.TextField("Totale Fatture ", Double.toString(tot+iva)));
        fatture.add(GenericComponent.TextField("Numero Pazienti ", StatisticController.getStatisticController().getNumeroPazienti()));

        this.add(fatture);
        this.add(new Container());

        //###########TERZA RIGA
        //isNoCF CHART
        this.add(RingGraph("Invia Codice Fiscale", pieDataset(StatisticController.getStatisticController().getCFDataSet())));
        this.add(new Container());

        this.add(BarGraph("", categoryDataset(StatisticController.getStatisticController().getCityDataSet())));

        //GOBACK
        this.add(new Container());
        this.add(GenericComponent.getBackButton());
        this.add(new Container());
    }

    private ChartPanel RingGraph(String title, DefaultPieDataset dataset){
        RingPlot plot = new RingPlot(dataset);
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(GraphicConst.BACKGROUND);

        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.CENTER);
        t.setPaint(GraphicConst.BACKGROUND_LIGHT);
        t.setFont(new Font("Arial", Font.BOLD, 26));

        plot.setBackgroundPaint(GraphicConst.BACKGROUND);
        plot.setOutlineVisible(false);
        plot.setSectionDepth(0.35);

        if(dataset.getItemCount() < 2){
            plot.setSimpleLabels(true);
        }

        plot.setLabelBackgroundPaint(GraphicConst.BACKGROUND_LIGHT);
        plot.setLabelPaint(GraphicConst.BACKGROUND_DARK);
        plot.setLabelShadowPaint(null);

        Font font=new Font("",0,16);
        plot.setLabelFont(font);
        chart.setPadding(new RectangleInsets(0,0,0,0));
        return new ChartPanel(chart);
    }

    private ChartPanel BarGraph(String title, DefaultCategoryDataset dataset){
        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                "",
                "",
                dataset,
                PlotOrientation.HORIZONTAL,
                false, false, false);

        barChart.setBackgroundPaint(GraphicConst.BACKGROUND);

        TextTitle t = barChart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.CENTER);
        t.setPaint(GraphicConst.BACKGROUND_LIGHT);
        t.setFont(new Font("Arial", Font.BOLD, 26));

        CategoryItemRenderer barColor = new CustomRenderer(new Paint[]{});
        barChart.getCategoryPlot().setRenderer(barColor);

        barChart.getCategoryPlot().setBackgroundPaint(new Color(0,0,0,0));
        barChart.getCategoryPlot().setRangeGridlinesVisible(false);
        barChart.getCategoryPlot().setOutlineVisible(false);
        barChart.getCategoryPlot().getRangeAxis().setAxisLineVisible(false);
        barChart.getCategoryPlot().getRangeAxis().setTickLabelsVisible(false);
        barChart.getCategoryPlot().getDomainAxis().setAxisLineVisible(false);
        barChart.getCategoryPlot().getDomainAxis().setTickLabelPaint(GraphicConst.BACKGROUND_DARK);
        return new ChartPanel(barChart);
    }

    private DefaultPieDataset pieDataset(Map<String, Long> dataset){
        DefaultPieDataset res = new DefaultPieDataset();
        dataset.forEach((k,v)->res.setValue(k,v));
        return res;
    }

    private DefaultCategoryDataset categoryDataset(Map<String, Long> dataset){
        DefaultCategoryDataset res = new DefaultCategoryDataset();
        dataset.forEach((k,v)->res.setValue(v,"", k));
        return res;
    }

    class CustomRenderer extends BarRenderer {

        private Paint[] colors =
                {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.CYAN,
                Color.WHITE, Color.MAGENTA, Color.PINK, Color.BLACK};
        public CustomRenderer(final Paint[] colors) {
            this.setBarPainter(new StandardBarPainter());
            this.setShadowVisible(false);
        }

        public Paint getItemPaint(final int row, final int column) {
            Random rand = new Random();
            rand.setSeed(column);
            return colors[rand.nextInt(colors.length)];
        }
    }
}
