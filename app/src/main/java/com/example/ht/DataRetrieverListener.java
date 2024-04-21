package com.example.ht;

public interface DataRetrieverListener {
    void onDataRetrieved(MunicipalityData data);
    void onDataRetrieveFailed(String errorMessage);
}
