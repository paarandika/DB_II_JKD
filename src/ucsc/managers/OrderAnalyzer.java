package ucsc.managers;

import org.neo4j.graphdb.Node;

import ucsc.db.Neo;


public class OrderAnalyzer {
	
	private static final String neo4j_path = "C:/Users/Raveen/Documents/Neo4j/ucsc/"; 
	
	Neo neo = new Neo();
	
	public void startAnalyzer()
	{
		neo.createDatabase(neo4j_path);
	}
	
	public void stopAnalyzer()
	{
		neo.shutdown();
	}
	
	public Node getOrder(long id)
	{
		Node order;
		order = neo.getNodeByKey(id);
		return order;
		
	}
	
	public Node addOrder(String key,String value, Node relative)
	{
		return neo.addNode(key, value, relative);
	}
	
	public Node addOrder(String key,String value)
	{
		return neo.addNode(key, value);
	}

}
