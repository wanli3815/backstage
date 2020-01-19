layui.define(["table", "form", "element"],
	function(e) {
		var q = layui.$,
			u = layui.util,
			t = layui.table;
		// 表格初始化
		t.render({
				elem: "#app-organization-list",
				url: 'getList',
				cellMinWidth: 180,
				//自定义响应字段
				response: {
					statusCode: 1 //数据状态一切正常的状态码
				},
				cols: [
					[{
							type: "numbers",
							width: 50,
							title: "序号",
						},
						{
							field: "organization_name",
							title: "营销组织名称",
							align: 'center'
						},
						{
							field: "projects",
							title: "关联营销项目",
							align: 'center'
						},
						{
							field: "teams",
							title: "销售团队 ",
							align: 'center'
						},
						{
							field: "organization_state",
							title: "状态",
							align: 'center'
						},
						
						{

							title: "操作",
							align: 'center',
							fixed: "right",
							templet: "#detail_tpl"

						},
					]
				],
				page: !0,
				limit: 10,
				limits: [10, 15, 20, 25, 30],
				text: "对不起，加载出现异常！"
			}),
			t.on("tool(app-organization-list)",
				function(e) {

					if("addteam" == e.event) {
						window.location.href="team?id=" + e.data.organization_id;
					}
					if("delete"==e.event){
						layer.confirm("确定删除此顾问么？",{title:'提示'},
                        function(d) {
                            q.ajax({
                                url: 'delete?id='+ e.data.adviser_id,
                                method:'POST',
                                success:function (res) {
                                    if (res.code == 1){
                                        e.del()
                                    }
                                    layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});
                                    layer.close(d); //关闭弹层
                                }
                            });
                        });
					}
					if("edit"==e.event){
						layer.open({
								type: 2,
								title: '编辑营销组织',
								content: 'add?organization_id='+e.data.organization_id,
								maxmin: true,
								area: ['750px', '500px'],
								btn: ['确定', '取消'],
								yes: function(index, layero) {
									//点击确认触发 iframe 内容中的按钮提交
									var l = window["layui-layer-iframe" + index],
										a = layero.find("iframe").contents().find("#app-organization-form-edit");
									l.layui.form.on("submit(app-organization-form-edit)",
											function(i) {
												var l = i.field;
												console.info(l.organization_state);
												if(l.organization_state=='on'){
													l.organization_state=1;
												}else{
													l.organization_state=0;
												}
												layui.$.post("edit", l, function(res) {
													if(res.code == 1) {

														table.reload("app-organization-list");
														layer.close(index);
													}
													layer.msg(res.msg, {
														icon: res.code == 1 ? 1 : 2,
														time: 1500
													});
												});
											}),
										a.trigger("click");
								}
							});
					}
					
				});
		e("fw_organization", {})
	});