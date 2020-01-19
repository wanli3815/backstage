layui.define(["table", "form", "element"],
    function(e) {
        var q = layui.$
            ,u = layui.util
            ,i = layui.table;
        // 表格初始化
        i.render({
            elem: "#app-facetoface-control-list",
            url: 'control_index_list',
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
                    field: "project", 
                    title: "营销项目",                  
                    align:'center'
                },
                {
                    field: "first_photo_time",
                    title: "首次拍摄时间",
                   	align:'center'
                },
                 {
                    field: "iscertsuc",
                    title: "是否认证成功",
                    align:'center'
                },
                 {
                    title: "查看照片",
                    align:'center',
                    templet:"#photo_tpl"
                },
                 {
                    field: "idcard",
                    title: "证件号码",
                   	align:'center'
                },
                 {
                    field: "idcardtime",
                    title: "身份证录入时间",
                    align:'center',
                     templet: function(d) {return u.toDateString(d.idcardtime*1000); }
                },
                 {
                    field: "agent_name",
                    title: "经纪人",
                    align:'center',
                   
                },
                 {
                    field: "agent_mobile",
                    title: "经纪人电话",
                    align:'center'
                },
                 {
                    field: "mechanism",
                    title: "经纪人公司",
                    Width: 200,
                    align:'center'
                },
                {
                    field: "recommend_time",
                    title: "推荐时间",
                    align:'center',
                    templet: function(d) {return u.toDateString(d.recommend_time*1000); }
                },
                 {
                    field: "subscription_time",
                    title: "认购时间",
                    align:'center',
                    templet: function(d) {return u.toDateString(d.subscription_time*1000); }
                },
                
                 {
                    title: "操作",
                    align:'center',
                   templet:"#detail_tpl"
                },
                ]],
            page: !0,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！"
        }),
            i.on("tool(app-facetoface-control-list)",
            function(e) {
                
                if("detail"==e.event){
                	layer.open({
	                    type: 2,
	                    title: e.data.client_name+"成交详情",
	                    content: "edit_detail.html?client_id=" + e.data.client_id,
	                    maxmin: !0,
	                    area: ['90%','85%'],
	                    btn: ["确定", "取消"],
	                    yes: function(e, i) {
	                        layer.close(e);
	                    }
                	});
                }
                if("photodetail"==e.event){
                	layer.open({
	                    type: 1,
	                    title:false,
	                    closeBtn: 0,
						area: '300px',
						skin: 'layui-layer-nobg', //没有背景色
						shadeClose: true,
	                    content: "<img width='300' src='"+e.data.photo+"' />",
                	});
                }
            });
        e("fw_face_control", {})
    });