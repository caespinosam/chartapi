package org.codechallenge.api.chart.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.codechallenge.api.chart.model.Category;
import org.codechallenge.api.chart.model.Dimension;
import org.springframework.stereotype.Repository;

/**
 * An implementation of {@link IChartDAO}. It uses an in-memory data structure
 * to mock data.
 * 
 * @author caespinosam
 *
 */
@Repository
public class ChartMockDAO implements IChartDAO {

	/**
	 * A mocked database to simulate dimensions, categories and measures.
	 * 
	 */
	private Map<String, Dimension> dimensions = new HashMap<>();

	/**
	 * Populates the mocked database.
	 */
	@PostConstruct
	private void simulateData() {

		Category catRM = new Category("Real Madrid");
		catRM.addMeasure("leagues", 33d);
		catRM.addMeasure("champions", 12d);
		catRM.addMeasure("revenue", 625d);

		Category catBAR = new Category("Barcelona");
		catBAR.addMeasure("leagues", 24d);
		catBAR.addMeasure("champions", 5d);
		catBAR.addMeasure("revenue", 620d);
		
		Category catBAY = new Category("Bayern Munich");
		catBAY.addMeasure("leagues", 26d);
		catBAY.addMeasure("champions", 5d);
		catBAY.addMeasure("revenue", 600d);

		Category catLIV = new Category("Liverpool");
		catLIV.addMeasure("leagues", 18d);
		catLIV.addMeasure("champions", 5d);
		catLIV.addMeasure("revenue", 400d);

		Category catMIL = new Category("Milan");
		catMIL.addMeasure("leagues", 18d);
		catMIL.addMeasure("champions", 7d);
		catMIL.addMeasure("revenue", 250d);

		Dimension dimTeam = new Dimension("team");
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
	public Dimension getDimensionData(String dimensionName) {

		return dimensions.get(dimensionName);
	}

}
