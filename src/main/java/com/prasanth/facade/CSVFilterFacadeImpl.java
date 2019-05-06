package com.prasanth.facade;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.prasanth.Exception.ParseOrFilterException;
import com.prasanth.model.Row;
import com.prasanth.service.CSVFileService;

public class CSVFilterFacadeImpl implements CSVFilterFacade{

	@Autowired
	private CSVFileService csvFileService;

	@Override
	public List<Row> handleUpload(Reader reader) throws ParseOrFilterException {

		try {
			return csvFileService.parseAndFilter(reader);
		} catch (Exception e) {
			throw new ParseOrFilterException(e.getMessage());
		}
	}

	@Override
	public List<String> buildHeader(List<Row> rows) {
		List<String> header = new ArrayList<String>();
		header.add("Id");
		for(int i=1; i<=rows.get(0).getVariables().size(); i++) {
			header.add("Var"+i);
		}
		header.add("Decision");
		return header;
	}
	
}
