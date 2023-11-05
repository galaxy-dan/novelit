import { groupType } from "@/model/charactor";
import { del, get, post, put } from "./http";

export const getGroup = async (uuid : string) => {
    const data = await get(`/group?groupUUID=${uuid}`);
    return data;
};
  
export const postGroup = async (body : groupType) => {
    const data = await post(`/group`, body);
    return data;
};
  
export const putGroup = async (body : groupType) => {
    const data = await put(`/group`, body);
    return data;
};

export const deleteGroup = async (uuid : string) => {
    const data = await del(`/group?groupUUID=${uuid}`);
    return data;
};

export const getGroupTop = async () => {
    const data = await get(`/group/top`);
    return data;
};
