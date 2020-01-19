layui.define(["table", "form", "element"],
    function(e) {
        var q = layui.$
            ,u = layui.util
            ,i = layui.table;
        // 表格初始化
        i.render({
            elem: "#app-agent-list",
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
                },
                {
                    field: "agent_name", 
                    title: "姓名",  
                    align:'center'
                },
                {
                    field: "agent_nickname", 
                    title: "昵称",                  
                    align:'center'
                },
                 {
                    field: "agent_mobile", 
                    title: "手机号 ",                  
                    align:'center'
                },
                {
                    field: "agent_type",
                    title: "经纪人类型",
                   	align:'center'
                },
                 {
                    field: "add_time",
                    title: "注册时间",
                    align:'center',
                     templet: function(d) {return u.toDateString(d.add_time*1000); }
                },
                 {
                    field: "authentication",
                    title: "实名认证状态",
                    align:'center'
                },
                 {
                    field: "bank_authentication",
                    title: "银行卡认证状态",
                   	align:'center'
                },
                 {
                    field: "state",
                    title: "账号状态",
                    align:'center'
                },
                 
                 {
                    
                    title: "操作",
                    align:'center',
                    fixed: "right",
                    templet:"#detail_tpl"

                },
                ]],
            page: !0,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！"
        }),
            i.on("tool(app-agent-list)",
            function(e) {
                
                if("detail"==e.event){
                	layer.open({
                    type: 2,
                    title: "项目设置",
                    content: "edit.html?id=" + e.data.agent_id,
                    maxmin: !0,
                    area: ['80%','60%'],
                    btn: ["确定", "取消"],
                    yes: function(e, i) {
                        layer.close(e);
                    }
                });
                }
            });
        e("fw_agent", {})
    });