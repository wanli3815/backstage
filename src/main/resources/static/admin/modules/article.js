layui.define(["table", "form"],
    function(t) {
        var q = layui.$,
            i = layui.table,
            u = layui.util,
            n = layui.form;
        i.render({
            elem: "#app-article-list",
            url: "getList",
            //自定义响应字段
            response: {
                statusName: 'code' //数据状态的字段名称
                ,statusCode: 1 //数据状态一切正常的状态码
                ,msgName: 'msg' //状态信息的字段名称
                ,dataName: 'data' //数据详情的字段名称
            },
            loading:true,
            cols: [[{
                type: "checkbox",
                fixed: "left"
            },
                {
                    field: "id",
                    width: 100,
                    title: "ID",
                    sort: !0
                },
                {
                    field: "title",
                    title: "标题"
                },
                {
                    field: "author",
                    title: "作者"
                },

                {
                    field: "createTime",
                    title: "发布时间",
                    sort: !0,
                },
                {
                    field: "updateTime",
                    title: "更新时间",
                    sort: !0,
                },
                {
                    field: "status",
                    title: "发布状态",
                    templet: "#buttonTpl",
                    minWidth: 80,
                    align: "center"
                },
                {
                    title: "操作",
                    minWidth: 150,
                    align: "center",
                    fixed: "right",
                    toolbar: "#table-content-list"
                }]],
            page: !0,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！"
        }),
            i.on("tool(app-article-list)",
                function(t) {
                var e = t.data;
                "del" === t.event ? layer.confirm("确定删除此文章？",
                    function(d) {
                        q.ajax({
                            url: 'del',
                            data:{
                                id:e.id,
                                obj:e
                            },
                            method:'DELETE',
                            success:function (res) {
                                if (res.code == 1){
                                    t.del()
                                }
                                layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});
                                layer.close(d); //关闭弹层
                            }
                        });
                    }) : "edit" === t.event && layer.open({
                    type: 2,
                    title: "编辑文章",
                    content: "edit/" + e.id,
                    maxmin: !0,
                    maxmin: !0,                             area: ['80%','60%'],
                    btn: ["确定", "取消"],
                    yes: function(e, i) {
                        var submit = i.find('iframe').contents().find("#app-article-form-edit");
                        submit.click();
                    }
                })
            });
            t("article", {})
    });