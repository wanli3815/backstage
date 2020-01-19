layui.define(["table", "form", "element"],
    function(e) {
        var q = layui.$
            ,u = layui.util
            ,i = layui.table;
        // 表格初始化
        i.render({
            elem: "#app-developmentbroker-list",
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
                    field: "adviser_name", 
                    title: "顾问姓名",
                  
                    align:'center'
                },
                {
                    field: "adviser_mobile", 
                    title: "顾问手机号",
                    align:'center'
                },
                 {
                    field: "team", 
                    title: "顾问所在团队",
                    align:'center'
                },
                {
                    field: "agent_name",
                    title: "经纪人姓名",
                   	align:'center'
                },
                {
                    field: "agent_mobile",
                    title: "经纪人手机号",
                   	align:'center'
                },
                 {
                    field: "agent_type",
                    title: "经纪人类型",
                    align:'center',
          
                },
                 {
                    field: "bind_time",
                    title: "绑定时间",
                    align:'center',
                    templet: function(d) {return u.toDateString(d.bind_time*1000); }
                },
                
                ]],
            page: !0,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！"
        });
        e("fw_developmentbroker", {})
    });