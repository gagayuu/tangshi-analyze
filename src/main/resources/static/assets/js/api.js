function creationRanking(id) {

    //http method:GET
    //http url
    $.get({
        url: "/analyze/author_count",
        dataType: "json",
        method: "get",
        success: function (data, status, xhr) {
            //echarts图表对象
            var myChart = echarts.init(document.getElementById(id));
            var options = {
                title: {
                    text: '创作排行榜'
                },
                tooltip: {},
                //提示信息
                legend: {
                    data: ['数量(首)']
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [{
                    name: '创作数量',
                    type: 'bar',
                    data: []
                }]
            };

            //List<AuthorCount>
            for (var i=0;i<data.length;i++) {
                var authorCount=data[i];
                options.xAxis.data.push(authorCount.author);
                options.series[0].data.push(authorCount.count);
            }
            myChart.setOption(options, true);
        },
        error: function (xhr, status, error) {

        }
    });
}

function cloudWorld(id) {
    $.get({
        url: "/analyze/word_cloud",
        dataType: "json",
        method: "get",
        success: function (data, status, xhr) {
            var myChart = echarts.init(document.getElementById(id));
            var options = {
                series: [{
                    type: 'wordCloud',
                    shape: 'pentagon',
                    left: 'center',
                    top: 'center',
                    width: '80%',
                    height: '80%',
                    right: null,
                    bottom: null,
                    sizeRange: [12, 60],
                    rotationRange: [-90, 90],
                    rotationStep: 45,
                    gridSize: 8,
                    drawOutOfBound: false,
                    textStyle: {
                        normal: {
                            fontFamily: 'sans-serif',
                            fontWeight: 'bold',
                            //随机设置字体颜色
                            color: function () {
                                return 'rgb(' + [
                                    Math.round(Math.random() * 160),
                                    Math.round(Math.random() * 160),
                                    Math.round(Math.random() * 160)
                                ].join(',') + ')';
                            }
                        },
                        emphasis: {
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },
                    // Data is an array. Each array item must have name and value property.
                    data: []
                }]
            };
            for (var i=0;i<data.length;i++) {
                var word=data[i];
                options.series[0].data.push({
                    name: word.word,
                    value: word.count,
                    textStyle: {
                        normal: {},
                        emphasis: {}
                    }
                });
            }
            myChart.setOption(options, true);
        },
        error: function (xhr, status, error) {

        }
    });
}