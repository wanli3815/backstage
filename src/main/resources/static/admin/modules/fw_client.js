layui.define(["table", "form", "element"],
    function(e) {
        var q = layui.$
            ,u = layui.util
            ,i = layui.table;
        // 表格初始化
        console.info("aaaa");
        i.render({
            elem: "#app-client-list",
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
                    sort: !0,
                    fixed: "left"
                },
                {
                    field: "client_nickname", 
                    title: "客户昵称",
                    fixed: "left",
                   	templet: '#nickname_tpl',
                    align:'center'
                },
                {
                    field: "client_mobile", 
                    title: "客户手机号",
                    templet: '#mobile_tpl',
                    fixed: "left",
                   
                    align:'center'
                },
                 {
                    field: "client_name", 
                    title: "客户姓名",
                     templet: '#realname_tpl',
                    fixed: "left",
                   
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
                    field: "distribution_state",
                    title: "分配状态",
                    align:'center'
                },
                 {
                    field: "inflow",
                    title: "流入原因",
                   	align:'center'
                },
                 {
                    field: "intention_level",
                    title: "意向等级",
                    align:'center'
                },
                 {
                    field: "addtime",
                    title: "新增时间",
                    align:'center',
                    minWidth:200,
                    width:200,
                    templet: function(d) {return u.toDateString(d.lastmodifiedtime*1000); }
                },
                 {
                    field: "recommender_nickname",
                    title: "推荐人昵称",
                    align:'center'
                },
                 {
                    field: "recommender_mobile",
                    title: "推荐人手机号",
                    Width: 200,
                    align:'center'
                },
                {
                    field: "recommender_organization",
                    title: "推荐人所属机构",
                    align:'center'
                },
                 {
                    field: "recommender_role",
                    title: "推荐人角色",
                    align:'center'
                },
                 {
                    field: "is_effective",
                    title: "推荐状态",
                    align:'center'
                },
                 {
                    field: "adviser_name",
                    title: "服务顾问姓名",
                    align:'center'
                },
                 {
                    field: "team",
                    title: "服务顾问团队",
                    align:'center'
                },
                 {
                    field: "cognitive",
                    title: "认知途径",
                    align:'center'
                },
                 {
                    field: "source",
                    title: "来源途径",
                    align:'center'
                },
                 {
                    field: "last_follow_time",
                    title: "最近跟进时间",
                    align:'center',
                    templet: function(d) {return u.toDateString(d.lastmodifiedtime*1000); }
                },
                 {
                    field: "overdue_time",
                    title: "服务逾期时间",
                    align:'center',
                    templet: function(d) {return u.toDateString(d.lastmodifiedtime*1000); }
                },
                 {
                    field: "subscription_time",
                    title: "认购时间",
                    align:'center',
                    templet: function(d) {return u.toDateString(d.lastmodifiedtime*1000); }
                },
                ]],
            page: !0,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！"
        }),
            i.on("tool(app-client-list)",
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
        e("fw_client", {})
    });