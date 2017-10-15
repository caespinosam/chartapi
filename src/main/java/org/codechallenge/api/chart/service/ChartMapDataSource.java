package org.codechallenge.api.chart.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.codechallenge.api.chart.model.ChartCategory;
import org.codechallenge.api.chart.model.ChartDimension;
import org.springframework.stereotype.Repository;

/**
 * An implementation of {@link IChartDataSource}. It uses an in-memory data structure
 * to mock data.
 * 
 * @author caespinosam
 *
 */
@Repository
public class ChartMapDataSource implements IChartDataSource {

	/**
	 * A mocked database to simulate dimensions, categories and measures.
	 * 
	 */
	private Map<String, ChartDimension> dimensions = new HashMap<>();

	/**
	 * Populates the mocked database.
	 */
	@PostConstruct
	private void simulateData() {

		ChartCategory catRM = new ChartCategory("Real Madrid");
		catRM.addMeasure("leagues", 33d);
		catRM.addMeasure("champions", 12d);
		catRM.addMeasure("revenue", 625d);

		ChartCategory catBAR = new ChartCategory("Barcelona");
		catBAR.addMeasure("leagues", 24d);
		catBAR.addMeasure("champions", 5d);
		catBAR.addMeasure("revenue", 620d);
		
		ChartCategory catBAY = new ChartCategory("Bayern Munich");
		catBAY.addMeasure("leagues", 26d);
		catBAY.addMeasure("champions", 5d);
		catBAY.addMeasure("revenue", 600d);

		ChartCategory catLIV = new ChartCategory("Liverpool");
		catLIV.addMeasure("leagues", 18d);
		catLIV.addMeasure("champions", 5d);
		catLIV.addMeasure("revenue", 400d);

		ChartCategory catMIL = new ChartCategory("Milan");
		catMIL.addMeasure("leagues", 18d);
		catMIL.addMeasure("champions", 7d);
		catMIL.addMeasure("revenue", 250d);

		ChartDimension dimTeam = new ChartDimension("team");
		dimTeam.addCategory(catRM);
		dimTeam.addCategory(catBAR);
		dimTeam.addCategory(catBAY);
		dimTeam.addCategory(catLIV);
		dimTeam.addCategory(catMIL);

		dimensions.put(dimTeam.getName(), dimTeam);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.codechallenge.api.chart.IChartDAO#getDimensionData(java.lang.String)
	 */
	@Override
	public ChartDimension getDimensionData(String dimensionName) {

		return dimensions.get(dimensionName);
	}

}
