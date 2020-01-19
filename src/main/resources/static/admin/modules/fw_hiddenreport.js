layui.define(["table", "form", "element"],
    function(e) {
        var q = layui.$
            ,u = layui.util
            ,i = layui.table;
        // 表格初始化
        i.render({
            elem: "#app-hiddenreport-list",
            url: 'getList',
            cellMinWidth:180,
            //自定义响应字段
            response: {
                statusCode: 1 //数据状态一切正常的状态码
            },
            cols: [[
                {
                   type: "numbers",
                    width: 50,
                    title: "序号",
                   
                  
                },
                {
                    field: "client_name", 
                    title: "客户姓名",
                  
                    align:'center'
                },
                {
                    field: "client_mobile", 
                    title: "客户手机号",
                    align:'center'
                },
                 {
                    field: "recommender", 
                    title: "推荐人姓名",
                   
                   
                    align:'center'
                },
                {
                    field: "recommender_mobile",
                    title: "推荐人手机号",
                   	align:'center'
                },
                 {
                    field: "reporting_time",
                    title: "报备时间",
                    align:'center',
                    templet: function(d) {return u.toDateString(d.reporting_time*1000); }
                },
                 {
                    field: "overdue_time",
                    title: "逾期时间",
                    align:'center',
                    templet: function(d) {return u.toDateString(d.overdue_time*1000); }
                },
                
                 {
                    field: "completion_time",
                    title: "补全时间",
                    align:'center',
                    templet: function(d) {return u.toDateString(d.completion_time*1000); }
                },
                 {
                    field: "status",
                    title: "隐号报备状态",
                   	align:'center'
                },
                
                
                ]],
            page: !0,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！"
        }),
            i.on("tool(app-hiddenreport-list)",
            function(e) {
                
                if("edit"==e.event){
                	layer.open({
                    type: 2,
                    title: "项目设置",
                    content: "edit.html?id=" + e.data.client_id,
                    maxmin: !0,
                    area: ['80%','60%'],
                    btn: ["确定", "取消"],
                    yes: function(e, i) {
                        layer.close(e);
                    }
                });
                }
            });
        e("fw_hiddenreport", {})
    });