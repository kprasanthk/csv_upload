package com.prasanth.service;

import java.io.Reader;
import java.util.List;

import com.prasanth.model.Row;

public interface CSVFileService {

	public List<Row> parseAndFilter( Reader reader) throws Exception;
}
