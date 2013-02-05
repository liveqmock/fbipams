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
    private StreamedContent barchart08; //电子银行排名
    private StreamedContent barchart05; //借记卡排名
    private StreamedContent barchart04; //存款排名

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
        this.barchart08 = createHorizontalBarChart(createCategoryDatasetForNumber("08"), null, null, "电子银行业绩排名", "barchart08");
        this.barchart05 = createHorizontalBarChart(createCategoryDatasetForNumber("05"), null, null, "借记卡业绩排名", "barchart05");
        this.barchart04 = createHorizontalBarChart(createCategoryDatasetForAmt("04"), null, null, "存款业绩排名(单位:万元)", "barchart04");
//        createBarChart(createCategoryDataset());
    }

    private void makePieChart() {
        try {
            JFreeChart jfreechart = ChartFactory.createPieChart("各产品总量分布", createPieDataset(), true, true, true);

            jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));

            PiePlot piePlot = (PiePlot) jfreechart.getPlot();//图表区域对象
            piePlot.setLabelFont(new Font("宋体", Font.PLAIN, 11));

            jfreechart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 11));

            File chartFile = new File("dynamichart");
            ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 550, 400);
            piechart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {
            logger.error("统计图表生成错误", e);
            throw new RuntimeException("统计图表生成错误", e);
        }
    }

    /**
     * 业绩评价数据总量
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
     * 业绩评价 数量型某产品 排名
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
     * 横向图
     *
     * @param xName      x轴的说明（如种类，时间等）
     * @param yName      y轴的说明（如速度，时间等）
     * @param chartTitle 图标题
     * @param fileName   生成图片的名字
     * @return
     */
    public DefaultStreamedContent createHorizontalBarChart(CategoryDataset dataset, String xName, String yName, String chartTitle, String fileName) {
        JFreeChart chart = ChartFactory.createBarChart(chartTitle, // 图表标题
                xName, // 目录轴的显示标签
                yName, // 数值轴的显示标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                false, // 是否显示图例(对于简单的柱状图必须是false)
                false, // 是否生成工具
                false // 是否生成URL链接
        );

        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));

        CategoryPlot plot = chart.getCategoryPlot();
        // 数据轴精度
        NumberAxis vn = (NumberAxis) plot.getRangeAxis();
        //设置刻度必须从0开始
        // vn.setAutoRangeIncludesZero(true);
        DecimalFormat df = new DecimalFormat("#0");
        vn.setNumberFormatOverride(df); // 数据轴数据标签的显示格式

        CategoryAxis domainAxis = plot.getDomainAxis();

        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的
        // Lable
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);

        domainAxis.setLabelFont(labelFont);// 轴标题
        domainAxis.setTickLabelFont(labelFont);// 轴数值

        domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);// 横轴上的 Lable 是否完整显示
        // domainAxis.setVerticalCategoryLabels(false);
        plot.setDomainAxis(domainAxis);

        ValueAxis rangeAxis = plot.getRangeAxis();
        // 设置最高的一个 Item 与图片顶端的距离
        rangeAxis.setUpperMargin(0.15);
        // 设置最低的一个 Item 与图片底端的距离
        rangeAxis.setLowerMargin(0.15);
        plot.setRangeAxis(rangeAxis);
        BarRenderer renderer = new BarRenderer();
        // 设置柱子宽度
        renderer.setMaximumBarWidth(30);
        // 设置柱子高度
        renderer.setMinimumBarLength(10);

        renderer.setBaseOutlinePaint(Color.BLACK);

        /*
        // 设置柱的颜色
        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setSeriesPaint(1, new Color(0, 0, 255));
        // 设置每个地区所包含的平行柱的之间距离
        renderer.setItemMargin(0.1);
        // 显示每个柱的数值，并修改该数值的字体属性
        */
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        // 设置柱的数值可见
        renderer.setBaseItemLabelsVisible(true);

        plot.setRenderer(renderer);
        // 设置柱的透明度
        //plot.setForegroundAlpha(0.6f);

        try {
            File chartFile = new File(fileName);
            ChartUtilities.saveChartAsPNG(chartFile, chart, 610, 400);
            return new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {
            logger.error("统计图表生成错误", e);
            throw new RuntimeException("统计图表生成错误", e);
        }

    }

    /**
     * 柱状图
     *
     * @param dataset    数据集
     * @param xName      x轴的说明（如种类，时间等）
     * @param yName      y轴的说明（如速度，时间等）
     * @param chartTitle 图标题
     * @param charName   生成图片的名字
     * @return
     */
    public String createBarChart(CategoryDataset dataset, String xName,
                                 String yName, String chartTitle, String charName) {
        JFreeChart chart = ChartFactory.createBarChart(chartTitle, // 图表标题
                xName, // 目录轴的显示标签
                yName, // 数值轴的显示标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                true, // 是否显示图例(对于简单的柱状图必须是false)
                false, // 是否生成工具
                false // 是否生成URL链接
        );
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
        /*
        * VALUE_TEXT_ANTIALIAS_OFF表示将文字的抗锯齿关闭,
        * 使用的关闭抗锯齿后，字体尽量选择12到14号的宋体字,这样文字最清晰好看
        */
        // chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        chart.setTextAntiAlias(false);
        chart.setBackgroundPaint(Color.white);
        // create plot
        CategoryPlot plot = chart.getCategoryPlot();
        // 设置横虚线可见
        plot.setRangeGridlinesVisible(true);
        // 虚线色彩
        plot.setRangeGridlinePaint(Color.gray);

        // 数据轴精度
        NumberAxis vn = (NumberAxis) plot.getRangeAxis();
        // vn.setAutoRangeIncludesZero(true);
        DecimalFormat df = new DecimalFormat("#0.00");
        vn.setNumberFormatOverride(df); // 数据轴数据标签的显示格式
        // x轴设置
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(labelFont);// 轴标题
        domainAxis.setTickLabelFont(labelFont);// 轴数值

        // Lable（Math.PI/3.0）度倾斜
        // domainAxis.setCategoryLabelPositions(CategoryLabelPositions
        // .createUpRotationLabelPositions(Math.PI / 3.0));

        domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// 横轴上的 Lable 是否完整显示

        // 设置距离图片左端距离
        domainAxis.setLowerMargin(0.1);
        // 设置距离图片右端距离
        domainAxis.setUpperMargin(0.1);
        // 设置 columnKey 是否间隔显示
        // domainAxis.setSkipCategoryLabelsToFit(true);

        plot.setDomainAxis(domainAxis);
        // 设置柱图背景色（注意，系统取色的时候要使用16位的模式来查看颜色编码，这样比较准确）
        plot.setBackgroundPaint(new Color(255, 255, 204));

        // y轴设置
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(labelFont);
        rangeAxis.setTickLabelFont(labelFont);
        // 设置最高的一个 Item 与图片顶端的距离
        rangeAxis.setUpperMargin(0.15);
        // 设置最低的一个 Item 与图片底端的距离
        rangeAxis.setLowerMargin(0.15);
        plot.setRangeAxis(rangeAxis);

        BarRenderer renderer = new BarRenderer();
        // 设置柱子宽度
        renderer.setMaximumBarWidth(0.05);
        // 设置柱子高度
        renderer.setMinimumBarLength(0.2);
        // 设置柱子边框颜色
        renderer.setBaseOutlinePaint(Color.BLACK);
        // 设置柱子边框可见
        renderer.setDrawBarOutline(true);

        // // 设置柱的颜色
        renderer.setSeriesPaint(0, new Color(204, 255, 255));
        renderer.setSeriesPaint(1, new Color(153, 204, 255));
        renderer.setSeriesPaint(2, new Color(51, 204, 204));

        // 设置每个地区所包含的平行柱的之间距离
        renderer.setItemMargin(0.0);

        // 显示每个柱的数值，并修改该数值的字体属性
        renderer.setIncludeBaseInRange(true);
        renderer
                .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);

        plot.setRenderer(renderer);
        // 设置柱的透明度
        plot.setForegroundAlpha(1.0f);

        try {
            File chartFile = new File("hbarchart");
            ChartUtilities.saveChartAsPNG(chartFile, chart, 600, 400);
            this.barchart08 = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {
            logger.error("统计图表生成错误", e);
            throw new RuntimeException("统计图表生成错误", e);
        }

        return null;
    }


}

