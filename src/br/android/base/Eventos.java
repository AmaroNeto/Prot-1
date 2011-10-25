package br.android.base;

public class Eventos extends Entretenimento{
	
	private String local;
	private String status;
	private String dataInicio;
	private String dataFim;
	
	public Eventos(String nome,String id, String local,String status,String dataInicio,String dataFim){
		super(nome,id);
		this.setLocal(local);
		this.setStatus(status);
		this.setDataInicio(dataInicio);
		this.setDataFim(dataFim);
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	public String getDataFim() {
		return dataFim;
	}
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	
}
