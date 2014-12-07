package ucsc.db;



import org.neo4j.cypher.internal.compiler.v2_1.planner.logical.plans.NodeIndexSeek;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.tooling.GlobalGraphOperations;

public class Neo {
	
	
	//private static Index<Node> nodeIndex; 
	GraphDatabaseService graphDatabaseService;
	
	private static enum RelTypes implements RelationshipType{
		Bought_by
	}
	
	
	public void createDatabase(String neo4j_path)
	{
		graphDatabaseService = new GraphDatabaseFactory().newEmbeddedDatabase(neo4j_path);
		//System.out.println("Started");
		
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
			
			Label myLabel = DynamicLabel.label(value);
			node.addLabel(myLabel);
			
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
			
			Label myLabel = DynamicLabel.label(value);
			node.addLabel(myLabel);
			
			node.createRelationshipTo(relativeNode, RelTypes.Bought_by);
			tx.success();
		}
		finally
		{
			tx.finish();
		}
		return node;
	}
	
	
	public void addAbsentNode(String key,String value)
	{
		//Relationship relation;
		Node node;
		Label myLabel = DynamicLabel.label(value);
		Transaction tx = graphDatabaseService.beginTx();
		
		ResourceIterator<Node> iterator =graphDatabaseService.
				findNodesByLabelAndProperty(myLabel, key, value).iterator();
		try 
		{	
			if(iterator.hasNext()){
				//System.out.println("true");
			}
			else
			{
				node = graphDatabaseService.createNode();
				node.setProperty(key,value);
				node.addLabel(myLabel);
			}
			
			
			
			
			tx.success();	
			
		}
		
		finally
		{
			tx.finish();
		}

	}
	
	
	public void addAbsentNode(String key,String value, Node relative,int quantity)
	{
		//Relationship relation;
		Node node;
		Label myLabel = DynamicLabel.label(value);
		Transaction tx = graphDatabaseService.beginTx();
		
		ResourceIterator<Node> iterator =graphDatabaseService.
				findNodesByLabelAndProperty(myLabel, key, value).iterator();
		try 
		{	
			if(iterator.hasNext()){
				node = iterator.next();
				//System.out.println("true");
				Relationship relation = node.createRelationshipTo(relative, RelTypes.Bought_by);
				relation.setProperty("Bought by", quantity);
			}
			else
			{
				node = graphDatabaseService.createNode();
				node.setProperty(key,value);
				node.addLabel(myLabel);
				Relationship relation = node.createRelationshipTo(relative, RelTypes.Bought_by);
				relation.setProperty("Bought by", quantity);
			}
			
			
			
			
			tx.success();	
			
		}
		
		finally
		{
			tx.finish();
		}

	}
	
	
	public Node getNodeByLabel(String mylabel ,String key, String value)
	{
		Node node = null;
		Label myLabel = DynamicLabel.label(value);
		Transaction tx = graphDatabaseService.beginTx();
		
		ResourceIterator<Node> iterator =graphDatabaseService.
				findNodesByLabelAndProperty(myLabel, key, value).iterator();
		try 
		{	
			if(iterator.hasNext()){
				//System.out.println("true");
				node = iterator.next();
			}
			
			
			
			
			
			tx.success();	
			
		}
		
		finally
		{
			tx.finish();
		}
		
		return node;
	}
	
	
}
