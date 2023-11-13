import { EdgeType, NodeType, characterType, poop } from '@/model/charactor';
import { del, get, patch, post, put } from './http';
import { String } from 'aws-sdk/clients/cloudwatchevents';

const transformDiagramData = (characterData: any, groupData: any) => {
  let nodeDatas: NodeType[] = [];

  characterData.forEach((dat: any) =>
    nodeDatas.push({
      data: {
        id: dat.characterUUID,
        label: dat.characterName,
        type: 'character',
      },
      ...(dat.characterImage && {
        style: {
          backgroundImage: dat.characterImage,
          backgroundFit: 'cover cover',
        },
      }),
    }),
  );

  let edgeDatas: EdgeType[] = [];
  characterData.forEach((dat: any) => {
    dat.relations.forEach((element: any) => {
      if (element.targetUUID) {
        edgeDatas.push({
          data: {
            source: dat.characterUUID,
            target: element.targetUUID,
            label: element.content,
            type: 'character',
          },
        });
      }
    });
  });

  groupData.allGroupsAndCharacters.forEach((dat: any) => {
    nodeDatas.push({
      data: {
        id: dat.groupUUID,
        label: dat.groupName,
        type: 'group',
      },
    });
    Object.keys(dat.childGroups).forEach((target) => {
      edgeDatas.push({
        data: { source: dat.groupUUID, target: target, type: 'group' },
      });
    });
    Object.keys(dat.childCharacters).forEach((target) => {
      edgeDatas.push({
        data: { source: dat.groupUUID, target: target, type: 'group' },
      });
    });
  });

  return {
    nodes: nodeDatas,
    edges: edgeDatas,
  };
};

const transformCharacterDirectory = (data: any) => {

  const result: any[] = [];
  const skip: boolean[] = new Array(data.allGroupsAndCharacters.length).fill(
    false,
  );

  data.allGroupsAndCharacters.forEach((groups: any, i: number) => {
    //자식의 자식을 타고 가는 함수

    if (skip[i]) {
      return;
    }
    const goDeep = (key: string, name: string) => {
      let child: any[] = [];
      let curId = key;
      let curName = name;
      let curIndex = data.allGroupsAndCharacters.findIndex(
        (obj: any) => obj.groupUUID === curId,
      );
      let curData = data.allGroupsAndCharacters.find(
        (obj: any) => obj.groupUUID === curId,
      );
      if (curIndex === -1) {
        return null;
      }
      Object.keys(curData.childGroups).forEach((key2) => {
        let newData = goDeep(key2, curData.childGroups[key2]);
        console.log(newData);
        if (newData !== null) {
          child.push(newData);
        } else { 
          console.log("null 제외"+ key2);
        }
      });

      Object.keys(curData.childCharacters).forEach((key2) => {
        child.push({
          id: key2,
          name: curData.childCharacters[key2],
        });
      });

      skip[curIndex] = true;

      return {
        id: curId,
        name: curName,
        children: child,
      };
    };

    let childrenData: any[] = [];

    Object.keys(groups.childGroups).forEach((key) => {
      // 초기 자식에 goDeep(key 추가)
      let newData = goDeep(key, groups.childGroups[key]);
      console.log(newData);
      if (newData !== null) {
        childrenData.push(newData);
      } else { 
        
      }
      //childrenData.push(goDeep(key, groups.childGroups[key]));
    });

    Object.keys(groups.childCharacters).forEach((key) => {
      childrenData.push({
        id: key,
        name: groups.childCharacters[key],
      });
    });

    result.push({
      id: groups.groupUUID,
      name: groups.groupName,
      children: childrenData,
    });
  });

  data.noGroupCharacters.forEach((character: any) => {
    result.push({
      id: character.characterUUID,
      name: character.characterName,
    });
  });

  return { name: '', children: result };
};

export const getCharacterByName = async (worksapce: string, name: string) => {
  if (name !== '') {
    const data = await get(
      `/character/search?workspaceUUID=${worksapce}&characterName=${name}`,
    );
    return data;
  } else {
    return [];
  }
};

export const getCharacter = async (uuid: string) => {
  const data = await get(`/character?characterUUID=${uuid}`);
  return data;
};

export const postCharacter = async (req: {
  workspace: string;
  group: string | null;
  name: string;
}) => {
  const data = await post(`/character`, {
    workspaceUUID: req.workspace,
    groupUUID: req.group,
    characterName: req.name,
    information: [{ '': '' }],
    relations: [{ content: '', targetName: '', targetUUID: null }],
  });
  return data;
};

export const patchCharacter = async (req: {
  params: string;
  body: characterType;
}) => {
  const data = await patch(`/character?characterUUID=${req.params}`, req.body);
  return data;
};

export const deleteCharacter = async (uuid: string) => {
  const data = await del(`/character?characterUUID=${uuid}`);
  return data;
};

export const getRelationDiagramInformation = async (workspace: string) => {
  const characterData = await get(`/character/diagram`);
  const groupData = await get(
    `/group/character/all?workspaceUUID=${workspace}`,
  );
  return transformDiagramData(characterData, groupData);
};

export const getCharacterDirectory = async (workspace: string) => {
  const data = await get(`/group/character/all?workspaceUUID=${workspace}`);
  const newData = transformCharacterDirectory(data);
  return newData;
};

export const patchCharacterName = async (req: {
  name: string;
  uuid: string;
}) => {
  const data = await get(`/character?characterUUID=${req.uuid}`);
  const newData = { ...data, characterName: req.name };
  const response = await patch(`/character?characterUUID=${req.uuid}`, newData);
  return response;
};
