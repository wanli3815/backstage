layui.define(["table", "form", "element"],
    function(e) {
        var q = layui.$
            ,u = layui.util
            ,i = layui.table;
        // 表格初始化
        i.render({
            elem: "#app-follow-list",
            url: 'getList',
            cellMinWidth:150,
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
                    field: "adviser_name",
                     align: "center",
                    title: "操作人"
                },
                {
                    field: "adviser_mobile",
                     align: "center",
                    title: "操作人电话"
                },
                {
                    field: "add_time",
                    title: "发生时间",
                    align: "center",
                    templet: function(d) {return u.toDateString(d.add_time*1000); }
                },
                {
                    field: "type",
                   minWidth: 80,
                    align: "center",
                    title: "操作类别",
                   
                },
                 {
                    field: "text",
                   minWidth: 80,
                    title: "跟进文本",
                   
                },
                 {
                    field: "client_name",
                   minWidth: 80,
                    align: "center",
                    title: "客户姓名",
                   
                },
                {
                	field:"client_mobile",
                    title: "客户电话",
                    align: "center",
                   
                }]],
            page: !0,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！"
        }),
        e("fw_follow", {})
    });