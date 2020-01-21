layui.define(["treeGrid","table", "form"],
    function(e) {
        var t = layui.$,
            b = layui.table,
            i = layui.treeGrid;
        // 表格初始化
        i.render({
            id:'app-admin-menu-list'
            ,elem: '#app-admin-menu-list'
            ,url: 'getList',

            response: {
                statusCode: 1 //数据状态一切正常的状态码
            }
            ,method:'get'//默认以post方式请求后台
            ,cellMinWidth: 100
            ,idField:'id'//必須字段
            ,treeId:'id'//树形id字段名称
            ,treeUpId:'pid'//树形父id字段名称
            ,treeShowName:'name'//以树形式显示的字段
            ,heightRemove:[".dHead",10]//不计算的高度,表格设定的是固定高度，此项不生效
            ,height:'100%'
            ,isFilter:false
            ,iconOpen:false//是否显示图标【默认显示】
            ,isOpenDefault:true//节点默认是展开还是折叠【默认展开】
            ,loading:true
            ,cols: [[ {
                    field: 'name',
                    width: 150,
                    title: '菜单名称',
                    edit: 'text',
                    sort: true
                },
                {
                    field:'perms',
                    width:200,
                    title:'授权'
                },{
                    field:'type',
                    title:'类型',
                    width:100,
                    templet: "#typeTpl",
                }
                ,{
                    field:'url',
                    width:200,
                    title: '链接'
                },{
                    field:'status',
                    title:'状态',
                    templet: "#statusTpl",
                }
                ,{
                    width: 220,
                    title: '操作',
                    align:'center',
                    templet:"#table-menu-list"

                }
            ]]
            ,isPage:false
            ,parseData:function (res) {
                return res;
            }
        });
            i.on('tool(app-admin-menu-list)',
                function (e) {
                    var d = e.data;
                    if ("del" === e.event) layer.confirm("确定删除此菜单？",
                        function(d) {
                            var loadindex=layer.msg('处理中...', {
                                icon: 16
                                ,shade: 0.01,
                                time:0
                            });
                            t.ajax({
                                url: 'del',
                                data:{
                                    id:e.data.id,
                                    obj:e.data
                                },
                                method:'DELETE',
                                success:function (res) {
                                    layer.close(index);
                                    if (res.code == 1){
                                        layer.close(d); //关闭弹层
                                        e.del();
                                    }
                                    layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});

                                }
                            });
                        });
                    else if ("add" === e.event) {
                        t(e.tr);
                        layer.open({
                            type: 2,
                            title: "新增菜单",
                            content: "add/"+ d.id,
                            maxmin: !0,                             area: ['80%','60%'],
                            btn: ["确定", "取消"],
                            yes: function(f, t) {
                                var l = window["layui-layer-iframe" + f],
                                    r = "app-admin-menu-submit",
                                    n = t.find("iframe").contents().find("#" + r);
                                    l.layui.form.on("submit("+ r +")",
                                        function(t) {
                                            var l = t.field;
                                            var loadindex=layer.msg('处理中...', {
                                                icon: 16
                                                ,shade: 0.01,
                                                time:0
                                            });
                                            layui.$.post("add",l,function (res) {
                                                layer.close(loadindex);
                                                if (res.code == 1){
                                                    b.render('app-admin-menu-list'),
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
                            title: "编辑菜单",
                            content: "edit/"+ d.id+"/"+ d.pid,
                            maxmin: !0,                             area: ['80%','60%'],
                            btn: ["确定", "取消"],
                            yes: function(f, t) {
                                var l = window["layui-layer-iframe" + f],
                                    r = "app-admin-menu-submit",
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
                                                    pid: l.pid,
                                                    name: l.name,
                                                    url: l.url,
                                                    perms: l.perms,
                                                    code: l.code
                                                }),
                                                    b.render('app-admin-menu-list'),
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
            });
        e("menu", {})
    });