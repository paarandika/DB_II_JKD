package ucsc.db;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Neo {
	
	

	GraphDatabaseService graphDatabaseService;
	
	private static enum RelTypes implements RelationshipType{
		BUYS
	}
	
	
	public void createDatabase(String neo4j_path)
	{
		graphDatabaseService = new GraphDatabaseFactory().newEmbeddedDatabase(neo4j_path);
		System.out.println("Started");
		
		//registerShutdownHook( graphDatabaseService);
		
		
		
	}
	
	public void shutdown()
	
	{
		System.out.println("Database Service is shutting down...");
		graphDatabaseService.shutdown();
		System.out.println("Database Service Terminated");
	}
	
	public Node addNode(String key,String value)
	{
		//Relationship relation;
		Node node;
		Transaction tx = graphDatabaseService.beginTx();
		
		try 
		{	
			node = graphDatabaseService.createNode();
			node.setProperty(key,value);
			tx.success();	
			
		}
		
		finally
		{
			tx.finish();
		}

		return node;
	}
	
	public Node addNode(String key,String value,Node relativeNode)
	{
		Node node;
		Transaction tx = graphDatabaseService.beginTx();
		
		try
		{
			node = graphDatabaseService.createNode();
			node.setProperty(key,value);
			node.createRelationshipTo(relativeNode, RelTypes.BUYS);
			tx.success();
		}
		finally
		{
			tx.finish();
		}
		return node;
	}
	
	public Node getNodeByKey(long id)
	{
		Node node = graphDatabaseService.getNodeById(id);
		return node;
	}
	
	
}
