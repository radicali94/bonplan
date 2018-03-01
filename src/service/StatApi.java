/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.Reaction;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author USER
 */
public class StatApi {
    public static ChartFrame drawChart(List<Reaction> R){
        int likes = (int) R.stream().filter(r->r.getType()==Reaction.ReactionType.like).count();
        int dislikes = (int) R.stream().filter(r->r.getType()==Reaction.ReactionType.dislike).count();
        DefaultPieDataset pieDataSet = new DefaultPieDataset();
        pieDataSet.setValue("liked", likes);
        pieDataSet.setValue("disliked",dislikes);
        JFreeChart chart = ChartFactory.createPieChart("pie chart",pieDataSet,true,true,true);
        PiePlot P=(PiePlot)chart.getPlot();
        ChartFrame frame = new ChartFrame("pie chart",chart);
        frame.setVisible(true);
        frame.setSize(100,100);
        return frame;
     
    }
    
}
