layui.define(["table", "form", "element"],
    function(e) {
        var q = layui.$
            ,u = layui.util
            ,i = layui.table;
        // 表格初始化
        console.info("aaaa");
        i.render({
            elem: "#app-visit-list",
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
                   
                    fixed: "left"
                },
                {
                    field: "projectname", 
                    title: "营销项目",
                    fixed: "left",
                    align:'center'
                },
                {
                    field: "room", 
                    title: "房间号",
                    fixed: "left",
                   
                    align:'center'
                },
                 {
                    field: "source", 
                    title: "客户来源途径",     
                    align:'center'
                },
                {
                    field: "cognitive",
                    title: "认知途径",
                   	align:'center'
                },
                 {
                    field: "orders_no",
                    title: "成交单联名购房标识",
                    align:'center'
                },
                 {
                    field: "subscription_time",
                    title: "认购时间",
                    align:'center'
                },
                 {
                    field: "subscription_money",
                    title: "认购金额",
                   	align:'center'
                },
                 {
                    field: "status",
                    title: "状态",
                    align:'center'
                },
                /* {
                    field: "addtime",
                    title: "新增时间",
                    align:'center',
                    minWidth:200,
                    width:200,
                    templet: function(d) {return u.toDateString(d.lastmodifiedtime*1000); }
                },*/
                 {
                    field: "client_name",
                    title: "客户姓名",
                    align:'center'
                },
                 {
                    field: "client_nickname",
                    title: "客户昵称",
                    Width: 200,
                    align:'center'
                },
                {
                    field: "client_mobile",
                    title: "客户手机",
                    align:'center'
                },
                 {
                    field: "first_visit_time",
                    title: "首次到访时间",
                    align:'center',
                     templet: function(d) {
                     	if(d.first_visit_time>0){
                     		return u.toDateString(d.first_visit_time*1000); 
                     	}
                     	return "--";
                     	
                     }
                },
                 {
                    field: "last_visit_time",
                    title: "最后到访时间",
                    align:'center',
                     templet: function(d) {
                     	if(d.last_visit_time>0){
                     		return u.toDateString(d.last_visit_time*1000); 
                     	}
                     	return "--";
                     	
                     }
                },
                 {
                    field: "recommend_time",
                    title: "推荐时间",
                    align:'center',
                     templet: function(d) {
                     	if(d.recommend_time>0){
                     		return u.toDateString(d.recommend_time*1000); 
                     	}
                     	return "--";
                     	
                     }
                },
                 {
                    field: "recommender",
                    title: "推荐人姓名",
                    align:'center'
                },
                 {
                    field: "recommender_nickname",
                    title: "推荐人昵称",
                    align:'center'
                },
                 {
                    field: "recommender_mobile",
                    title: "推荐人手机号",
                    align:'center'
                },
                 {
                    field: "recommender_role",
                    title: "推荐人角色",
                    align:'center',
                   
                },
                 {
                    field: "recommender_organization",
                    title: "所属机构",
                    align:'center',
                    
                },
                 {
                    field: "adviser_name",
                    title: "顾问姓名",
                    align:'center',
                   
                },
                 {
                    field: "adviser_mobile",
                    title: "顾问手机号",
                    align:'center',
                   
                },
                {
                    field: "team",
                    title: "顾问所属团队",
                    align:'center',
                   
                },
                ]],
            page: !0,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！"
        }),
            i.on("tool(app-visit-list)",
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
                        var l = window["layui-layer-iframe" + e],
                            a = i.find("iframe").contents().find("#app-article-form-edit");
                        l.layui.form.on("submit(app-article-form-edit)",
                            function(i) {
                                var l = i.field;
                                layui.$.post("edit",l,function (res) {
                                    if (res.code == 1){
                                        //更新数据表
                                        t.update({
                                            label: l.label,
                                            title: l.title,
                                            author: l.author,
                                            status: l.status
                                        }),
                                            n.render(),
                                                layer.close(e)
                                    }
                                    layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});
                                });
                            }),
                            a.trigger("click")
                    }
                });
                }
            });
        e("fw_order", {})
    });