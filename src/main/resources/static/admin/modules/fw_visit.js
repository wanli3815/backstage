layui.define(["table", "form", "element"],
    function(e) {
        var q = layui.$
            ,u = layui.util
            ,i = layui.table;
        // 表格初始化
        
        i.render({
            elem: "#app-visit-list",
            url: 'getList',
            cellMinWidth:180,
            //自定义响应字段
            response: {
                statusCode: 1 //数据状态一切正常的状态码
            },
            cols: [[{
                type: "checkbox",
                fixed: "left"
            },
                {
                   type: "numbers",
                    width: 50,
                    title: "序号",
                    fixed: "left"
                },
                {
                    field: "client_name", 
                    title: "客户姓名",
                    fixed: "left",
                    align:'center'
                },
                {
                    field: "client_mobile", 
                    title: "客户电话",
                    fixed: "left",
                   
                    align:'center'
                },
                 {
                    field: "visit_num", 
                    title: "到访总数",     
                    align:'center'
                },
                {
                    field: "visit_type",
                    title: "最新到访类型",
                   	align:'center'
                },
                 {
                    field: "intention_level",
                    title: "意向等级",
                    align:'center'
                },
                 {
                    field: "source",
                    title: "来源途径",
                    align:'center'
                },
                 {
                    field: "cognitive",
                    title: "认知途径",
                   	align:'center'
                },
                 {
                    field: "team",
                    title: "团队名称",
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
                    field: "adviser_name",
                    title: "顾问名称",
                    align:'center'
                },
                 {
                    field: "adviser_mobile",
                    title: "顾问电话",
                    Width: 200,
                    align:'center'
                },
                {
                    field: "client_visit_state",
                    title: "到访状态",
                    align:'center'
                },
                 {
                    field: "client_transaction_state",
                    title: "交易状态",
                    align:'center'
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
                    field: "first_visit_time",
                    title: "最近到访时间",
                    align:'center',
                     templet: function(d) {
                    	if(d.first_visit_time>0){
                    		return u.toDateString(d.first_visit_time*1000); 
                    	}
                    	return "--";
                    	
                    }
                },
                 {
                    field: "subscription_time",
                    title: "最近认购时间",
                    align:'center',
                     templet: function(d) {
                    	if(d.subscription_time>0){
                    		return u.toDateString(d.subscription_time*1000); 
                    	}
                    	return "--";
                    	
                    }
                },
                 {
                    field: "sign_time",
                    title: "最近签约时间",
                    align:'center',
                     templet: function(d) {
                    	if(d.sign_time>0){
                    		return u.toDateString(d.sign_time*1000); 
                    	}
                    	return "--";
                    	
                    }
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
        e("fw_visit", {})
    });