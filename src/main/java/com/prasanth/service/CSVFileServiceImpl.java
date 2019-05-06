package com.prasanth.service;

import static java.util.stream.Collectors.toList;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.prasanth.model.Row;

public class CSVFileServiceImpl implements CSVFileService{

	@Override
	public List<Row> parseAndFilter( Reader reader) throws Exception {
		// TODO Auto-generated method stub
		
		CSVReader csvReader= new CSVReaderBuilder(reader)
                .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                // Skip the header
                .withSkipLines(1)
                .build();
		
		String[] nextRecord;
		List<Row> allRows = new ArrayList<Row>();
		List<Integer> fmin = new ArrayList<Integer>();
		List<Integer> fmax = new ArrayList<Integer>();
		
        try {
			while ((nextRecord = csvReader.readNext()) != null) {
				Row row = new Row(new ArrayList<Integer>());
				for(int i=0; i<nextRecord.length; i++) {
					if(i==0) {
						row.setId(Integer.parseInt(nextRecord[i]));
					}else if (i==nextRecord.length-1) {
						row.setDecision(Integer.parseInt(nextRecord[i]));
					}else {
						row.getVariables().add(Integer.parseInt(nextRecord[i]));
						if(Integer.parseInt(nextRecord[nextRecord.length-1])==1) {
							if(fmin.size()<i) {
								fmin.add(Integer.parseInt(nextRecord[i]));
								fmax.add(Integer.parseInt(nextRecord[i]));
							}else {
								if(fmin.get(i-1) > Integer.parseInt(nextRecord[i])) {
									fmin.set(i-1, Integer.parseInt(nextRecord[i]));
								}
								if(fmax.get(i-1) < Integer.parseInt(nextRecord[i])) {
									fmax.set(i-1, Integer.parseInt(nextRecord[i]));
								}
							}
						}
					}
				}
				allRows.add(row);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
        return filter(allRows, fmin, fmax);
	}
	
	private List<Row> filter(List<Row> allRows, List<Integer> fmin, List<Integer> fmax) {
		List<Row> filteredRows = null;
		filteredRows = allRows.stream().filter(e -> e.getDecision()==1 || isValidRow(fmin, fmax, e.getVariables())).collect(toList());
		return filteredRows;
	}

	public boolean isValidRow(List<Integer> fmin, List<Integer> fmax, List<Integer> vars) {
		for(int i=0; i<vars.size(); i++) {
			if(vars.get(i)>=fmin.get(i) && vars.get(i)<=fmax.get(i)) {
				return true;
			}
		}
		return false;
	}
}
