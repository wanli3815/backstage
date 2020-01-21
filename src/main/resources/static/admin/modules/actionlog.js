layui.define(["table", "form", "element"],
    function(e) {
        var q = layui.$
            ,u = layui.util
            ,i = layui.table;
        // 表格初始化
        i.render({
            elem: "#app-admin-log-list",
            url: 'getList',
            //自定义响应字段
            response: {
                statusCode: 1 //数据状态一切正常的状态码
            },
            loading:true,
            cols: [[

                {
                    field: "username",
                    width: 100,
                    align: "center",
                    title: "操作人"
                },
                {
                    field: "operation",
                    width: 100,
                    align: "center",
                    title: "操作行为"
                },
                {
                    field: "businessType",
                    width: 100,
                    title: "业务类型",
                    align: "center",
                    templet: "#type_tpl"
                },
                {
                    field: "url",
                    align: "center",
                    title: "操作网址"
                },
                {
                    field: "params",
                    width: 350,
                    align: "center",
                    title: "请求参数"
                },
                {
                    field: "ip",
                    width: 180,
                    align: "center",
                    title: "IP"
                },
                {
                    field: "iplocation",
                    width: 180,
                    align: "center",
                    title: "IP所在地"
                },
                {
                    field: "createTime",
                    width: 200,
                    align: "center",
                    title: "操作时间",
                }
               ]],
            page: !0,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！"
        }),
            i.on("tool(app-admin-log-list)",
            function(e) {
                if ("del" === e.event){
                    layer.prompt({
                            formType: 1,
                            title: "敏感操作，请验证口令"
                        },
                        function(t, i) {
                            layer.close(i),
                                layer.confirm("真的删除么",
                                    function(t) {
                                        q.ajax({
                                            url: 'logDel?id='+ e.data.id,
                                            method:'POST',
                                            success:function (res) {
                                                if (res.code == 1){
                                                    e.del()
                                                }
                                                layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});
                                                layer.close(t); //关闭弹层
                                            }
                                        });
                                    })
                        });
                }
            });
        e("actionlog", {})
    });