package com.taotao.test.pagehelper;

public class TestPageHelper {
	/*@Test
	public void testHelper() {
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper mapper=(TbItemMapper)context.getBean(TbItemMapper.class);
		
		PageHelper.startPage(2, 3);
		TbItemExample example=new TbItemExample();
		List<TbItem> list = mapper.selectByExample(example);
		
		//取分页信息
		PageInfo<TbItem> info=new PageInfo<TbItem>(list);
		System.out.println("总记录数："+info.getTotal());
		System.out.println("size："+info.getSize());
		System.out.println("getFirstPage："+info.getFirstPage());
		System.out.println("getLastPage："+info.getLastPage());
		System.out.println("getPageSize："+info.getPageSize());
		
		for (TbItem tbItem : list) {
			System.out.println(tbItem);
		}
		
	}*/
}
