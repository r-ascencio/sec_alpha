
<!-- servlet: views.admin.AdminDashboardServlet.java -->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, 
                              pageContext.request.requestURI, 
                              pageContext.request.contextPath)}" />

<t:head pageTitle="Administracion ${entityName}">

    <jsp:attribute name="assets">

        <link rel="stylesheet" type="text/css" 
              href="${baseURL}/assets/js/jChartFX/styles/jchartfx.css" />
        <script type="text/javascript"
                src="${baseURL}/assets/js/jChartFX/js/jchartfx.system.js">
        </script>
        <script type="text/javascript"
                src="${baseURL}/assets/js/jChartFX/js/jchartfx.coreBasic.js">
        </script>
        <script type="text/javascript"
                src="${baseURL}/assets/js/jChartFX/js/jchartfx.bullet.js">
        </script>
        <script type="text/javascript"
                src="${baseURL}/assets/js/jChartFX/js/jchartfx.coreVector.js">
        </script>
        <script type="text/javascript"
                src="${baseURL}/assets/js/jChartFX/js/jchartfx.coreVector3d.js">
        </script>
        <script type="text/javascript"
                src="${baseURL}/assets/js/jChartFX/js/jchartfx.advanced.js">
        </script>
        <script type="text/javascript"
                src="${baseURL}/assets/js/jChartFX/js/jchartfx.animation.js">
        </script>
        <script type="text/javascript"
                src="${baseURL}/assets/js/jChartFX/js/jchartfx.data.js">
        </script>
        <script type="text/javascript"
                src="${baseURL}/assets/js/utils.js">
        </script>

        <style type="text/css">

            .progressbar-container {
                margin: 10px auto;
            }

        </style>

    </jsp:attribute>

</t:head>

<t:adminbody adminTitle="${adminTitle}" adminDescp="${adminDescp}">
    <jsp:attribute name="sidemenu">
        <t:adminsidemenu>
        </t:adminsidemenu>
    </jsp:attribute>

    <jsp:attribute name="body">

        <div id="div_Chart" style="width:100%;height:450px;"></div>
        <hr/>
        <div id="ChartDiv" style="width:100%;height:775px;display:inline-block"></div>

        <!-- <div class="twelvecol">
            <div id="candidatoPie" class="k-content twelvecol">
                <div class="candidatoPie-wrapper">

                    <div id="candidatoGrafica" style="background: center no-repeat url(${baseURL}/assets/img/bg.png">
                    </div>

                    <div id="tipChartInfo" class="tooltipPop" style="display:none;">
                        <div id="detailsChart" style="width:400px;height:300px;
                             display:inline-block">       
                        </div>
                    </div>

                </div>
            </div>
            <div class="twelvecol" style="text-align: center">
                            <div  class="twelvecol">
                                <div class="Barchart-wrapper" style='height: 600px;'>
                                    <div id="Barchart" sstyle="background: center no-repeat url('${baseURL}/assets/img/bg.png')"></div>
                                </div>
                            </div>
                            <div class="twelvecol" id="progress-cont">
                                 here you will see the processs. 
                            </div>
                        </div>-->
    </jsp:attribute>
</t:adminbody>
<t:footer>
    <jsp:attribute name="foot">
        <!-- i should write a script for init and shit -->
        <!--
        <script type="text/javascript">
                        var data = ${dataCandidatos},
                                dataVotacion = ${dataVotacion},
                                espArr = [],
                                espPrcnt = [],
                                n = 0;
                        var fieldPie = "${fieldPie}";
        
                        for (var i = 0; i < dataVotacion.length; i++) {
        
                            espArr[i] = dataVotacion[i].nombre;
        
                            espPrcnt[i] = getPercent(
                                    dataVotacion[i].alumnos,
                                    dataVotacion[i].votaciones_realizadas
                                    );
        
                            n++;
                        }
        
                    </script>-->
        <script type="text/javascript" language="javascript">
            var chart1;
            var chart2;
            var divInTooltip = null;

            function loadChart()
            {
                chart1 = new cfx.Chart();
                chart1.getAnimations().getLoad().setEnabled(true);

                chart1.setGallery(cfx.Gallery.Bar);
                chart1.getAxisX().setLabelAngle(27);
                chart1.getView3D().setEnabled(true);
                chart1.getView3D().setRotated(true);
                chart1.getView3D().setAngleX(30);
                chart1.getView3D().setAngleY(-20);
                chart1.getView3D().setBoxThickness(90);
                chart1.getView3D().setDepth(80);
                chart1.getAxisY().setMin(0);
                chart1.getAxisY().setMax(100);
                chart1.getLegendBox().setVisible(false);

                chart1.setDataSource(${dataVotacion});
                doTitle("Proceso de Votación");
                var divHolder = document.getElementById('ChartDiv');
                chart1.create(divHolder);

            }

            //Chart title settings
            function doTitle(text) {
                var titles = chart1.getTitles();
                if (titles.getCount() === 0) {
                    var title = new cfx.TitleDockable();
                    title.setText(text);
                    titles.add(title);
                } else {
                    titles.clear();
                }
            }

            loadChart();

            function onGetTipDiv(args) {
                if (args.getHitType() == cfx.HitType.Point) {
                    if (divInTooltip === null) {
                        divInTooltip = document.getElementById('tipChartInfo');
                        args.tooltipDiv.appendChild(divInTooltip);
                        divInTooltip.style.visibility = "hidden";
                        divInTooltip.style.display = "block";

                        var divHolder = document.getElementById('detailsChart');
                        divHoder.innerHTML = "";
                        divInTooltip.style.visibility = "inherit";
                    } else
                        args.replaceDiv = false;
                }
            }


            var td;
            chart2 = new cfx.Chart();
            chart2.getAnimations().getLoad().setEnabled(true);
            td = new cfx.TitleDockable();
            td.setText("Puntaje votación");
            chart2.getTitles().add(td);
            chart2.getLegendBox().setVisible(true);
            chart2.setGallery(cfx.Gallery.Pie);
            chart2.getAllSeries().getPointLabels().setVisible(true);
            chart2.getView3D().setEnabled(true);
            var myPie;
            myPie = (chart2.getGalleryAttributes());
            myPie.setExplodingMode(cfx.ExplodingMode.All);
            myPie.setSliceSeparation(10);
            var data = ${dataCandidatos}

            chart2.setDataSource(data);
            chart2.create(document.getElementById('div_Chart'))


        </script>

        <style type="text/css">
            #targetchart {
                width: 100%;
                height: 375px;
                padding: 4px;
                border: 0px solid #dddddd;
                background: #eeeeee
            }

            #targetchart h3 {
                text-align: center;
            }
        </style>

        <style type="text/css"
               href="${baseURL}/assets/js/jChartFX/styles/jChartFX Palettes/lite.css" >
        </style>

<!-- <script src="${baseURL}/assets/js/charts.js">
</script>-->

    </jsp:attribute>
</t:footer>