package com.prasanth.facade;

import java.io.Reader;
import java.util.List;

import com.prasanth.Exception.ParseOrFilterException;
import com.prasanth.model.Row;

public interface CSVFilterFacade {

	public List<Row> handleUpload(Reader reader) throws ParseOrFilterException;
	
	public List<String> buildHeader(List<Row> rows);
}
