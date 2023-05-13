package in.tube.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import in.tube.model.CoronaModel;
import jakarta.annotation.PostConstruct;

@Service
public class CoronaService {

	
	private static String uri = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/"
			+ "csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	private List<CoronaModel> model=new ArrayList<>();

	

	public List<CoronaModel> getModel() {
		return model;
	}

	@PostConstruct
	public void fetchVirusData() throws IOException, InterruptedException {
		List<CoronaModel> updatableModel=new ArrayList<>();
		HttpClient client =HttpClient.newHttpClient();
		
		HttpRequest request= HttpRequest.newBuilder().
				uri(URI.create(uri)).build();
		
			HttpResponse<String> httpResponse;
		
		httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
			
		StringReader csvReader= new StringReader(httpResponse.body());
		Iterable<CSVRecord> records=CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
		for(CSVRecord recordd: records) {
			CoronaModel cm=new CoronaModel();
			cm.setState(recordd.get("Province/State"));
			cm.setCountry(recordd.get("Country/Region"));
			int latest=Integer.parseInt(recordd.get(recordd.size()-1));
			int diff=latest-Integer.parseInt(recordd.get(recordd.size()-2));
			cm.setLatestCount(latest);
			cm.setDiffFromPrev(diff);
			updatableModel.add(cm);
			//System.out.println(cm);
		}
		this.model=updatableModel;
		
	}
}
