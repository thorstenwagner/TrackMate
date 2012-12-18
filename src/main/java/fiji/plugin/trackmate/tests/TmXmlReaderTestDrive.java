package fiji.plugin.trackmate.tests;

import java.io.File;

import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import fiji.plugin.trackmate.TrackMateModel;
import fiji.plugin.trackmate.TrackMate_;
import fiji.plugin.trackmate.io.TmXmlReader;

public class TmXmlReaderTestDrive {

	private static final File file = new File("/Users/tinevez/Desktop/Data/FakeTracks.xml");
	//	private static final File file = new File("/Users/tinevez/Projects/ELaplantine/2011-06-29/Dish4_avg-cell1.xml");
	//	private static final File file = new File("/Users/tinevez/Projects/DMontaras/Mutant/20052011_16_20.xml");

	public static <T extends RealType<T> & NativeType<T>> void main(String args[]) {

		//		ij.ImageJ.main(args);

		System.out.println("Opening file: "+file.getAbsolutePath());		
		TrackMate_<T> plugin = new TrackMate_<T>();
		plugin.initModules();
		TmXmlReader<T> reader = new TmXmlReader<T>(file, plugin);
		if (!reader.checkInput() && !reader.process()) {
			System.err.println("Problem loading the file:");
			System.err.println(reader.getErrorMessage());
		}
		TrackMateModel<T> model = plugin.getModel();

		System.out.println(model);
		
//		 Instantiate displayer
		fiji.plugin.trackmate.visualization.AbstractTrackMateModelView<T> displayer 
			= new fiji.plugin.trackmate.visualization.hyperstack.HyperStackDisplayer<T>();
		displayer.setModel(model);
		displayer.render();
		displayer.refresh();

	}


}
