package ucsc.managers;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterable;

import ucsc.db.Neo;


public class OrderAnalyzer {
	
	private static final String neo4j_path = "Neo4j_db/ucsc/"; 
	
	Neo neo = new Neo();
	
	public void startAnalyzer()
	{
		neo.createDatabase(neo4j_path);
	}
	
	public void stopAnalyzer()
	{
		neo.shutdown();
	}
	
	public Node getOrder(String mylabel ,String key, String value)
	{
		Node node;
		Label myLabel = DynamicLabel.label(value);
		node = neo.getNodeByLabel(mylabel,key,value);
		return node;
		
	}
	
	public Node addOrder(String key,String value, Node relative)
	{
		return neo.addNode(key, value, relative);
	}
	
	public Node addOrder(String key,String value)
	{
		return neo.addNode(key, value);
	}
	
	public void addAbset(String key,String value)
	{
		neo.addAbsentNode(key, value);
	}
	
	public void addAbset(String key,String value,Node relative,int quantity)
	{
		neo.addAbsentNode(key, value, relative,quantity);
	}

}
