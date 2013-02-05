package pams.view.common;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pams.common.SystemService;
import pams.repository.dao.SvintvinfMapper;
import pams.repository.dao.SvprdsalinfMapper;
import pams.repository.model.prdset.PsPrdStatisticBean;
import pams.repository.model.prdset.PsPrdsetStatisticBean;
import pams.repository.model.telemarketing.SalesStatusBean;
import pams.repository.model.telemarketing.chart.SalesPrdStatusBean;
import skyline.service.PlatformService;
import pams.service.prdset.PsSalesInfoService;
import pams.service.prdset.PsStatisticService;
import pams.service.telemarketing.TmSalesInfoService;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-3-18
 * Time: ????11:20
 * To change this template use File | Settings | File Templates.
 */


@ManagedBean
//@ViewScoped
public class TrackMiscAction implements Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{tmSalesInfoService}")
    private TmSalesInfoService tmSalesInfoService;
    @ManagedProperty(value = "#{psSalesInfoService}")
    private PsSalesInfoService psSalesInfoService;
    @ManagedProperty(value = "#{psStatisticService}")
    private PsStatisticService psStatisticService;

    @ManagedProperty(value = "#{svprdsalinfMapper}")
    private SvprdsalinfMapper salesMapper;
    @ManagedProperty(value = "#{svintvinfMapper}")
    private SvintvinfMapper interviewMapper;

    private StreamedContent piechart;
    private StreamedContent barchart08; //������������
    private StreamedContent barchart05; //��ǿ�����
    private StreamedContent barchart04; //�������

    private  List<PsPrdsetStatisticBean> prdsetStatisticList;
    private  List<PsPrdStatisticBean> prdStatisticList;


    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public void setTmSalesInfoService(TmSalesInfoService tmSalesInfoService) {
        this.tmSalesInfoService = tmSalesInfoService;
    }


    public StreamedContent getPiechart() {
        return piechart;
    }

    public void setPiechart(StreamedContent piechart) {
        this.piechart = piechart;
    }

    public SvprdsalinfMapper getSalesMapper() {
        return salesMapper;
    }

    public void setSalesMapper(SvprdsalinfMapper salesMapper) {
        this.salesMapper = salesMapper;
    }

    public SvintvinfMapper getInterviewMapper() {
        return interviewMapper;
    }

    public void setInterviewMapper(SvintvinfMapper interviewMapper) {
        this.interviewMapper = interviewMapper;
    }

    public void setPsSalesInfoService(PsSalesInfoService psSalesInfoService) {
        this.psSalesInfoService = psSalesInfoService;
    }

    public StreamedContent getBarchart08() {
        return barchart08;
    }

    public void setBarchart08(StreamedContent barchart08) {
        this.barchart08 = barchart08;
    }

    public StreamedContent getBarchart05() {
        return barchart05;
    }

    public void setBarchart05(StreamedContent barchart05) {
        this.barchart05 = barchart05;
    }

    public StreamedContent getBarchart04() {
        return barchart04;
    }

    public void setBarchart04(StreamedContent barchart04) {
        this.barchart04 = barchart04;
    }

    public List<PsPrdsetStatisticBean> getPrdsetStatisticList() {
        return prdsetStatisticList;
    }

    public void setPrdsetStatisticList(List<PsPrdsetStatisticBean> prdsetStatisticList) {
        this.prdsetStatisticList = prdsetStatisticList;
    }

    public void setPsStatisticService(PsStatisticService psStatisticService) {
        this.psStatisticService = psStatisticService;
    }

    public List<PsPrdStatisticBean> getPrdStatisticList() {
        return prdStatisticList;
    }

    public void setPrdStatisticList(List<PsPrdStatisticBean> prdStatisticList) {
        this.prdStatisticList = prdStatisticList;
    }
    //====================================================

    @PostConstruct
    public void init() {

        OperatorManager om = SystemService.getOperatorManager();
        String deptid = om.getOperator().getDeptid();

        this.prdsetStatisticList = psStatisticService.selectPrdsetStatisticData(deptid);
        this.prdStatisticList = psStatisticService.selectPrdStatisticData(deptid);

        makePieChart();
        this.barchart08 = createHorizontalBarChart(createCategoryDatasetForNumber("08"), null, null, "��������ҵ������", "barchart08");
        this.barchart05 = createHorizontalBarChart(createCategoryDatasetForNumber("05"), null, null, "��ǿ�ҵ������", "barchart05");
        this.barchart04 = createHorizontalBarChart(createCategoryDatasetForAmt("04"), null, null, "���ҵ������(��λ:��Ԫ)", "barchart04");
//        createBarChart(createCategoryDataset());
    }

    private void makePieChart() {
        try {
            JFreeChart jfreechart = ChartFactory.createPieChart("����Ʒ�����ֲ�", createPieDataset(), true, true, true);

            jfreechart.getTitle().setFont(new Font("����", Font.BOLD, 18));

            PiePlot piePlot = (PiePlot) jfreechart.getPlot();//ͼ���������
            piePlot.setLabelFont(new Font("����", Font.PLAIN, 11));

            jfreechart.getLegend().setItemFont(new Font("����", Font.PLAIN, 11));

            File chartFile = new File("dynamichart");
            ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 550, 400);
            piechart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {
            logger.error("ͳ��ͼ�����ɴ���", e);
            throw new RuntimeException("ͳ��ͼ�����ɴ���", e);
        }
    }

    /**
     * ҵ��������������
     *
     * @return
     */
    private PieDataset createPieDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        List<SalesStatusBean> salesList = tmSalesInfoService.analysisSalesDataForPieChart();
        for (SalesStatusBean salesStatusBean : salesList) {
            dataset.setValue(salesStatusBean.getPrdname(), salesStatusBean.getSalescount());
        }

        return dataset;
    }

    /**
     * ҵ������ ������ĳ��Ʒ ����
     *
     * @return
     */
    private CategoryDataset createCategoryDatasetForNumber(String prdid) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<SalesPrdStatusBean> salesList = tmSalesInfoService.analysisSalesDataForNumberTypeCategoryChart(prdid);
        for (SalesPrdStatusBean record : salesList) {
            dataset.addValue(record.getTotalnum(), record.getDeptname(), record.getDeptname());
        }

        return dataset;
    }
    private CategoryDataset createCategoryDatasetForAmt(String prdid) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<SalesPrdStatusBean> salesList = tmSalesInfoService.analysisSalesDataForAmtTypeCategoryChart(prdid);
        for (SalesPrdStatusBean record : salesList) {
            dataset.addValue(record.getTotalnum(), record.getDeptname(), record.getDeptname());
        }

        return dataset;
    }


    /**
     * ����ͼ
     *
     * @param xName      x���˵���������࣬ʱ��ȣ�
     * @param yName      y���˵�������ٶȣ�ʱ��ȣ�
     * @param chartTitle ͼ����
     * @param fileName   ����ͼƬ������
     * @return
     */
    public DefaultStreamedContent createHorizontalBarChart(CategoryDataset dataset, String xName, String yName, String chartTitle, String fileName) {
        JFreeChart chart = ChartFactory.createBarChart(chartTitle, // ͼ�����
                xName, // Ŀ¼�����ʾ��ǩ
                yName, // ��ֵ�����ʾ��ǩ
                dataset, // ���ݼ�
                PlotOrientation.VERTICAL, // ͼ����ˮƽ����ֱ
                false, // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
                false, // �Ƿ����ɹ���
                false // �Ƿ�����URL����
        );

        chart.getTitle().setFont(new Font("����", Font.BOLD, 18));

        CategoryPlot plot = chart.getCategoryPlot();
        // �����ᾫ��
        NumberAxis vn = (NumberAxis) plot.getRangeAxis();
        //���ÿ̶ȱ����0��ʼ
        // vn.setAutoRangeIncludesZero(true);
        DecimalFormat df = new DecimalFormat("#0");
        vn.setNumberFormatOverride(df); // ���������ݱ�ǩ����ʾ��ʽ

        CategoryAxis domainAxis = plot.getDomainAxis();

        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // �����ϵ�
        // Lable
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);

        domainAxis.setLabelFont(labelFont);// �����
        domainAxis.setTickLabelFont(labelFont);// ����ֵ

        domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);// �����ϵ� Lable �Ƿ�������ʾ
        // domainAxis.setVerticalCategoryLabels(false);
        plot.setDomainAxis(domainAxis);

        ValueAxis rangeAxis = plot.getRangeAxis();
        // ������ߵ�һ�� Item ��ͼƬ���˵ľ���
        rangeAxis.setUpperMargin(0.15);
        // ������͵�һ�� Item ��ͼƬ�׶˵ľ���
        rangeAxis.setLowerMargin(0.15);
        plot.setRangeAxis(rangeAxis);
        BarRenderer renderer = new BarRenderer();
        // �������ӿ��
        renderer.setMaximumBarWidth(30);
        // �������Ӹ߶�
        renderer.setMinimumBarLength(10);

        renderer.setBaseOutlinePaint(Color.BLACK);

        /*
        // ����������ɫ
        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setSeriesPaint(1, new Color(0, 0, 255));
        // ����ÿ��������������ƽ������֮�����
        renderer.setItemMargin(0.1);
        // ��ʾÿ��������ֵ�����޸ĸ���ֵ����������
        */
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        // ����������ֵ�ɼ�
        renderer.setBaseItemLabelsVisible(true);

        plot.setRenderer(renderer);
        // ��������͸����
        //plot.setForegroundAlpha(0.6f);

        try {
            File chartFile = new File(fileName);
            ChartUtilities.saveChartAsPNG(chartFile, chart, 610, 400);
            return new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {
            logger.error("ͳ��ͼ�����ɴ���", e);
            throw new RuntimeException("ͳ��ͼ�����ɴ���", e);
        }

    }

    /**
     * ��״ͼ
     *
     * @param dataset    ���ݼ�
     * @param xName      x���˵���������࣬ʱ��ȣ�
     * @param yName      y���˵�������ٶȣ�ʱ��ȣ�
     * @param chartTitle ͼ����
     * @param charName   ����ͼƬ������
     * @return
     */
    public String createBarChart(CategoryDataset dataset, String xName,
                                 String yName, String chartTitle, String charName) {
        JFreeChart chart = ChartFactory.createBarChart(chartTitle, // ͼ�����
                xName, // Ŀ¼�����ʾ��ǩ
                yName, // ��ֵ�����ʾ��ǩ
                dataset, // ���ݼ�
                PlotOrientation.VERTICAL, // ͼ����ˮƽ����ֱ
                true, // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
                false, // �Ƿ����ɹ���
                false // �Ƿ�����URL����
        );
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
        /*
        * VALUE_TEXT_ANTIALIAS_OFF��ʾ�����ֵĿ���ݹر�,
        * ʹ�õĹرտ���ݺ����御��ѡ��12��14�ŵ�������,���������������ÿ�
        */
        // chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        chart.setTextAntiAlias(false);
        chart.setBackgroundPaint(Color.white);
        // create plot
        CategoryPlot plot = chart.getCategoryPlot();
        // ���ú����߿ɼ�
        plot.setRangeGridlinesVisible(true);
        // ����ɫ��
        plot.setRangeGridlinePaint(Color.gray);

        // �����ᾫ��
        NumberAxis vn = (NumberAxis) plot.getRangeAxis();
        // vn.setAutoRangeIncludesZero(true);
        DecimalFormat df = new DecimalFormat("#0.00");
        vn.setNumberFormatOverride(df); // ���������ݱ�ǩ����ʾ��ʽ
        // x������
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(labelFont);// �����
        domainAxis.setTickLabelFont(labelFont);// ����ֵ

        // Lable��Math.PI/3.0������б
        // domainAxis.setCategoryLabelPositions(CategoryLabelPositions
        // .createUpRotationLabelPositions(Math.PI / 3.0));

        domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// �����ϵ� Lable �Ƿ�������ʾ

        // ���þ���ͼƬ��˾���
        domainAxis.setLowerMargin(0.1);
        // ���þ���ͼƬ�Ҷ˾���
        domainAxis.setUpperMargin(0.1);
        // ���� columnKey �Ƿ�����ʾ
        // domainAxis.setSkipCategoryLabelsToFit(true);

        plot.setDomainAxis(domainAxis);
        // ������ͼ����ɫ��ע�⣬ϵͳȡɫ��ʱ��Ҫʹ��16λ��ģʽ���鿴��ɫ���룬�����Ƚ�׼ȷ��
        plot.setBackgroundPaint(new Color(255, 255, 204));

        // y������
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(labelFont);
        rangeAxis.setTickLabelFont(labelFont);
        // ������ߵ�һ�� Item ��ͼƬ���˵ľ���
        rangeAxis.setUpperMargin(0.15);
        // ������͵�һ�� Item ��ͼƬ�׶˵ľ���
        rangeAxis.setLowerMargin(0.15);
        plot.setRangeAxis(rangeAxis);

        BarRenderer renderer = new BarRenderer();
        // �������ӿ��
        renderer.setMaximumBarWidth(0.05);
        // �������Ӹ߶�
        renderer.setMinimumBarLength(0.2);
        // �������ӱ߿���ɫ
        renderer.setBaseOutlinePaint(Color.BLACK);
        // �������ӱ߿�ɼ�
        renderer.setDrawBarOutline(true);

        // // ����������ɫ
        renderer.setSeriesPaint(0, new Color(204, 255, 255));
        renderer.setSeriesPaint(1, new Color(153, 204, 255));
        renderer.setSeriesPaint(2, new Color(51, 204, 204));

        // ����ÿ��������������ƽ������֮�����
        renderer.setItemMargin(0.0);

        // ��ʾÿ��������ֵ�����޸ĸ���ֵ����������
        renderer.setIncludeBaseInRange(true);
        renderer
                .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);

        plot.setRenderer(renderer);
        // ��������͸����
        plot.setForegroundAlpha(1.0f);

        try {
            File chartFile = new File("hbarchart");
            ChartUtilities.saveChartAsPNG(chartFile, chart, 600, 400);
            this.barchart08 = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {
            logger.error("ͳ��ͼ�����ɴ���", e);
            throw new RuntimeException("ͳ��ͼ�����ɴ���", e);
        }

        return null;
    }


}

