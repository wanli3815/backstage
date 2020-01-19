layui.define(["table", "form", "element"],
    function(e) {
        var q = layui.$
            ,u = layui.util
            ,i = layui.table;
        // 表格初始化
        i.render({
            elem: "#app-project-list",
            url: 'getList',
            //自定义响应字段
            response: {
                statusCode: 1 //数据状态一切正常的状态码
            },
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
                    field: "projectname",
                    
                    title: "项目名称"
                },
                {
                    field: "lastmodifiedadmin",
                    
                    title: "最后修改人"
                },
                {
                    field: "lastmodifiedtime",
                   
                    title: "最后修改时间",
                    templet: function(d) {return u.toDateString(d.lastmodifiedtime*1000); }
                },
                {
                    field: "projectstatus",
                   minWidth: 80,
                    title: "项目状态",
                   
                },
                {
                    title: "操作",
                    align: "center",
                     minWidth: 150,
                    fixed: "right",
                    toolbar: "#table-content-list"
                }]],
            page: !0,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！"
        }),
            i.on("tool(app-project-list)",
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
                //项目规则设置
                if("setting"==e.event){
                	layer.open({
                    type: 2,
                    title: "项目设置",
                    content: "setting.html?id=" + e.data.id,
                    maxmin: !0,
                    maxmin: !0,
                    area: ['80%','60%'],
                    btn: ["确定", "取消"],
                    yes: function(index, layero) {
                        var iframeWindow = window['layui-layer-iframe'+ index]
                            ,submitID = 'app-article-form-submit'
                            ,submit = layero.find('iframe').contents().find('#'+ submitID);
						
                        //监听提交
                        iframeWindow.layui.form.on('submit('+ submitID +')', function(obj){
                            var field = obj.field; //获取提交的字段

                            //提交 Ajax 成功后，静态更新表格中的数据
                            q.ajax({
                                url:'setting',
                                method:'POST',
                                data:field,
                                success:function (res) {
                                    if (res.code == 1){
                                        table.reload('app-user-manage'); //数据刷新
                                    }
                                    layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});
                                    layer.close(index); //关闭弹层
                                }
                            });
                        });

                        submit.trigger('click');
                    }
                });
                }
            });
        e("fw_project", {})
    });