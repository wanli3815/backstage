layui.define(["table", "form","authtree"],
    function(e) {
        var t = layui.$,
            i = layui.table,
            u = layui.util,
            n = layui.form,
            authtree=layui.authtree;

        i.render({
            elem: "#app-admin-user-manage",
            url: "userList",
            cellMinWidth: 180,
            //自定义响应字段
            response: {
                statusCode: 1 //数据状态一切正常的状态码
            },
            cols: [
                [{
                    field: "username",
                    title: "登录名",
                    align: "center"
                },{
                    field: "realName",
                    title: "真实姓名",
                    align: "center"
                }, {
                    field: "sex",
                    title: "性别",
                    templet: "#sexTpl",
                    align: "center"
                },{
                    field: "phone",
                    title: "手机号",
                    align: "center"
                },{
                    field:"create_id",
                    title:"创建人",
                    align: "center"
                }, {
                    field: "createTime",
                    title: "创建时间"
                }, {
                    field: "status",
                    title: "审核状态",
                    templet: "#buttonTpl",
                    minWidth: 80,
                    align: "center"
                }, {
                    title: "操作",
                    width: 230,
                    align: "center",
                    fixed: "right",
                    toolbar: "#table-useradmin-admin"
                }]
            ],
            page:1,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！"
        }),
        i.on("tool(app-admin-user-manage)",
            function(e) {
                var d = e.data;
                if ("del" === e.event) layer.prompt({
                        formType: 1,
                        title: "敏感操作，请验证口令"
                    },
                    function(d, i) {
                        layer.close(i),
                            layer.confirm("确定删除此管理员？",
                                function(d) {
                                    console.log(e),
                                        t.ajax({
                                            url: 'del/'+ e.data.id,
                                            method:'DELETE',
                                            success:function (res) {
                                                if (res.code == 1){
                                                    e.del()
                                                }
                                                layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});
                                                layer.close(d); //关闭弹层
                                            }
                                        });
                                })
                    });
                else if ("auth" === e.event) {
                    t(e.tr);
                    layer.open({
                        type: 2,
                        title: "管理授权",
                        content: "userauth/"+ d.id,
                        maxmin: !0,                             area: ['80%','60%'],
                        btn: ["确定", "取消"],
                        yes: function(f, t) {
                            var l = window["layui-layer-iframe" + f],
                                r = "app-admin-user-auth-submit",
                                n = t.find("iframe").contents().find("#" + r);
                            l.layui.form.on("submit("+ r +")",
                                function(t) {
                                    var l = t.field;
                                    layui.$.post("userauth",l,function (res) {
                                        if (res.code == 1){
                                            i.render(),
                                                layer.close(f)
                                        }
                                        layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});
                                    });
                                }),
                                n.trigger("click")
                        },
                        success: function(e, t) {}
                    })
                }
                else  if ("edit" === e.event) {
                    t(e.tr);
                    layer.open({
                        type: 2,
                        title: "编辑管理员",
                        content: "edit/"+ d.id,
                        maxmin: !0,                             area: ['80%','60%'],
                        btn: ["确定", "取消"],
                        yes: function(f, t) {
                            var l = window["layui-layer-iframe" + f],
                                r = "app-admin-user-back-submit",
                                n = t.find("iframe").contents().find("#" + r);
                            l.layui.form.on("submit(" + r + ")",
                                function(d) {
                                    var l = d.field;
                                    var loadindex=layer.msg('处理中...', {
                                        icon: 16
                                        ,shade: 0.01,
                                        time:0
                                    });
                                    layui.$.post("edit",l,function (res) {
                                        layer.close(loadindex);
                                        if (res.code == 1){
                                            //更新数据表
                                            e.update({
                                                username: l.username,
                                                realName: l.realName,
                                                phone: l.phone,
                                                sex: l.sex,
                                                email: l.email,
                                                status: l.status
                                            }),i.render(),
                                                layer.close(f)
                                        }
                                        layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});
                                    });
                                }),
                                n.trigger("click")
                        },
                        success: function(e, t) {}
                    })
                }
            }),
        i.render({
            elem: "#app-admin-user-role",
            url: "roleList",
            //自定义响应字段
            response: {
                statusCode: 1 //数据状态一切正常的状态码
            },
            cols: [
                [ {
                    field: "id",
                    width: 200,
                    title: "ID",

                }, {
                    field: "name",
                    width: 100,
                    title: "角色名"
                }, {
                    field: "description",
                    title: "具体描述"
                }, {
                    field: "createTime",
                    title: "创建时间"
                }, {
                    title: "操作",
                    width: 220,
                    align: "center",
                    fixed: "right",
                    toolbar: "#table-admin-user-role"
                }]
            ],
            page:1,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！"
        }),
        i.on("tool(app-admin-user-role)",
            function(e) {
                var loadindex=layer.msg('处理中...', {
                    icon: 16
                    ,shade: 0.01,
                    time:0
                });
                var d = e.data;
                if ("del" === e.event) layer.confirm("确定删除此角色？",
                    function(d) {
                        t.ajax({
                            url: 'del/'+ e.data.id,
                            method:'Delete',
                            success:function (res) {
                                layer.close(loadindex);
                                if (res.code == 1){
                                    e.del()
                                }
                                layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});
                                layer.close(d); //关闭弹层
                            }
                        });
                    });
                else if ("auth" === e.event) {
                    t(e.tr);
                    layer.open({
                        type: 2,
                        title: "角色授权",
                        content: "auth/"+ d.id,
                        maxmin: !0,                             area: ['80%','60%'],
                        btn: ["确定", "取消"],
                        yes: function(f, t) {
                            var l = window["layui-layer-iframe" + f],
                                r = "app-admin-user-role-submit";
                            n = t.find("iframe").contents().find("#" + r),
                            temps=t.find("iframe").contents().find("#app-auth-tree-list");
                            l.layui.form.on("submit("+ r +")",
                                function(t) {
                                    var loadindex=layer.msg('处理中...', {
                                        icon: 16
                                        ,shade: 0.01,
                                        time:0
                                    });
                                    var resources = authtree.getChecked(temps);
                                    resources = resources.join(",");
                                    console.info(resources);
                                   // return false;
                                    layui.$.post("auth",{id:d.id,menuids:resources},function (res) {
                                        layer.close(loadindex);
                                        if (res.code == 1){
                                            i.render(),
                                                layer.close(f)
                                        }
                                        layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});
                                    });
                                }),
                                n.trigger("click")
                        },
                        success: function(e, t) {}
                    })
                }
                else if ("edit" === e.event) {
                    t(e.tr);
                    layer.open({
                        type: 2,
                        title: "编辑角色",
                        content: "edit/"+ d.id,
                        maxmin: !0,                             area: ['80%','60%'],
                        btn: ["确定", "取消"],
                        yes: function(f, t) {
                            var l = window["layui-layer-iframe" + f],
                                r = "app-admin-user-role-submit";
                                n = t.find("iframe").contents().find("#" + r);
                            l.layui.form.on("submit("+ r +")",
                                function(t) {
                                    var l = t.field;
                                    var loadindex=layer.msg('处理中...', {
                                        icon: 16
                                        ,shade: 0.01,
                                        time:0
                                    });
                                    layui.$.post("edit",l,function (res) {
                                        layer.close(loadindex);
                                        if (res.code == 1){
                                            //更新数据表
                                            e.update({
                                                name: l.name,
                                                description: l.description
                                            }),i.render(),
                                                layer.close(f)
                                        }
                                        layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});
                                    });
                                }),
                                n.trigger("click")
                        },
                        success: function(e, t) {}
                    })
                }
            }),
        e("useradmin", {})
    });
