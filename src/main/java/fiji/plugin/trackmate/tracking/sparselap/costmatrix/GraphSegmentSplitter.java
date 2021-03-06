package fiji.plugin.trackmate.tracking.sparselap.costmatrix;

import fiji.plugin.trackmate.Spot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;

public class GraphSegmentSplitter
{
	private final List< Spot > segmentStarts;

	private final List< Spot > segmentEnds;

	private final List< List< Spot >> segmentMiddles;

	public GraphSegmentSplitter( final UndirectedGraph< Spot, DefaultWeightedEdge > graph, final boolean findMiddlePoints )
	{
		final ConnectivityInspector< Spot, DefaultWeightedEdge > connectivity = new ConnectivityInspector< Spot, DefaultWeightedEdge >( graph );
		final List< Set< Spot >> connectedSets = connectivity.connectedSets();
		final Comparator< Spot > framecomparator = Spot.frameComparator;

		segmentStarts = new ArrayList< Spot >( connectedSets.size() );
		segmentEnds = new ArrayList< Spot >( connectedSets.size() );
		if ( findMiddlePoints )
		{
			segmentMiddles = new ArrayList< List< Spot > >( connectedSets.size() );
		}
		else
		{
			segmentMiddles = Collections.emptyList();
		}

		for ( final Set< Spot > set : connectedSets )
		{
			if ( set.size() < 2 )
			{
				continue;
			}

			final List< Spot > list = new ArrayList< Spot >( set );
			Collections.sort( list, framecomparator );

			segmentEnds.add( list.remove( list.size() - 1 ) );
			segmentStarts.add( list.remove( 0 ) );
			if ( findMiddlePoints )
			{
				segmentMiddles.add( list );
			}
		}
	}

	public List< Spot > getSegmentEnds()
	{
		return segmentEnds;
	}

	public List< List< Spot >> getSegmentMiddles()
	{
		return segmentMiddles;
	}

	public List< Spot > getSegmentStarts()
	{
		return segmentStarts;
	}

}
