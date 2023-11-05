import { characterType } from "@/model/charactor";
import { del, get, post, put } from "./http";

export const getCharacter = async (uuid : string) => {
    const data = await get(`/character?characterUUID=${uuid}`);
    return data;
};
  
export const postCharacter = async (body : characterType) => {
    const data = await post(`/character`, body);
    return data;
};
  
export const putCharacter = async (params:string, body: characterType) => {
    const data = await put(`/character?characterUUID=${params}`, body);
    return data;
};

export const deleteCharacter = async (uuid : string) => {
    const data = await del(`/character?characterUUID=${uuid}`);
    return data;
};
