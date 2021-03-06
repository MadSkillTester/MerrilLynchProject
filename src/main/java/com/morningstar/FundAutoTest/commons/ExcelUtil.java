package com.morningstar.FundAutoTest.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.morningstar.FundAutoTest.PathUtil;


public class ExcelUtil {

	/**
	 * Get excel cell data into a list which contains string array as element
	 * 
	 * @param excel
	 *            the excel file to be read
	 * @param sheetName
	 *            name of the sheet to be read
	 * @return
	 */
	public static List<List<String>> getCellData(String excel, String sheetName) throws Exception {

		ExcelAnalysisXLSX ana = new ExcelAnalysisXLSX();
//		ana.setPath(PathUtil.getAbsolutePath(excel));
		ana.setPath(excel);
		ana.setSheetName(sheetName);
		ana.process();
		List<List<String>> datas = ana.getDatas();
		return datas;
	}
//若第三个参数设置成true，则从excel表中读取到的数据为删除首行内容的数据
	public static List<List<String>> getCellData(String excel, String sheetName, boolean ignoreHeader) throws Exception {

		ExcelAnalysisXLSX ana = new ExcelAnalysisXLSX();
		ana.setPath(PathUtil.getAbsolutePath(excel));
		ana.setSheetName(sheetName);
		ana.process();
		List<List<String>> datas = ana.getDatas();
		if (!ignoreHeader)
			return datas;
		else {
			datas.remove(0);
			return datas;
		}
	}

	/**
	 * To get columns'data from excel
	 * 
	 * @param excel
	 * @param sheetName
	 * @param columns
	 * @param ignoreHeader
	 * @return
	 * @throws Exception
	 */
	public static List<List<String>> getColumnData(String excel, String sheetName, int[] columns, boolean ignoreHeader) throws Exception {

		if (columns == null || columns.length == 0)
			return null;

		Map<Integer, List<String>> maps = new HashMap<Integer, List<String>>();
		for (int i = 0; i < columns.length; i++) {
			maps.put(columns[i], new ArrayList<String>());
		}

		List<List<String>> rowDatas = getCellData(excel, sheetName);

		for (int i = 0; i < rowDatas.size(); i++) {

			if (i == 0 && ignoreHeader)
				continue;

			List<String> row = rowDatas.get(i);
			for (int j = 0; j < columns.length; j++) {
				if (columns[j] > row.size() - 1)
					continue;
				maps.get(columns[j]).add(row.get(columns[j]));
			}

		}

		List<List<String>> result = new ArrayList<List<String>>();
		result.addAll(maps.values());

		return result;
	}
	
	public static void main(String[] args) throws Exception{
		String excel = "C:/HJG_WORK/CalculatorModel/TestModel(3_year_risk_monthly).xlsx";
		int[] columns = {1};
		List<List<String>> list = new ArrayList<List<String>>();
		list = getColumnData(excel,"Risk free monthly market return",columns,false);
		for(List<String> subList :list){
			for(String str :subList){
				System.out.println(str);
			}
		}				
	}
}
