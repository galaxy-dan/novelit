import { plotType } from "@/model/plot";
import { del, get, post, put } from "./http";

export const getPlotList = async(workspaceUuid : string, keyword: string) => {
    const data = await get(`/plot?workspaceUuid=${workspaceUuid}&keyword=${keyword}`);
    return data;
};
export const getPlot = async (uuid : string) => {
    const data = await get(`/plot/detail?plotUuid=${uuid}`);
    return data;
};

export const postPlot = async (body : plotType) => {
    const data = await post(`/plot`, body);
    return data;
};
  
export const putPlot = async (params:string, body: plotType) => {
    const data = await put(`/plot?plotUuid=${params}`, {...body, plotUuid: params});
    return data;
};

export const deletePlot = async (uuid : string) => {
    const data = await del(`/plot?plotUuid=${uuid}`);
    return data;
};
