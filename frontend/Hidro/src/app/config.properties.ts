export class Config {
}

export class AppSettings {
    //private static API_SERVER: string ='lpz-p-213412';    
    // private static API_SERVER: string ='10.11.10.56'; 
    private static API_SERVER: string ='172.16.0.91'; // test

    public static API_ENDPOINT_PRESUPUESTO='http://'+ AppSettings.API_SERVER +':9400/api-fenix';
    public static API_ENDPOINT_EMPRESAS='http://'+ AppSettings.API_SERVER +':9300/api-fenix';
    public static API_ENDPOINT_CONTRATOS='http://'+ AppSettings.API_SERVER +':9200/api-fenix';
    public static API_ENDPOINT_PARAMETRICAS='http://'+ AppSettings.API_SERVER +':9100/api-fenix';    
    
}


