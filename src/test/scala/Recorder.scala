import com.excilys.ebi.gatling.recorder.config.RecorderOptions
import com.excilys.ebi.gatling.recorder.controller.RecorderController

object Recorder extends App {

	RecorderController(new RecorderOptions(
		outputFolder = Some(IDEPathHelper.recorderOutputDirectory.toString),
		simulationPackage = Some("de.objective.jsf.gatling"),
		requestBodiesFolder = Some(IDEPathHelper.requestBodiesDirectory.toString)))
}