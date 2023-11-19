import { EdgeType, NodeType, characterType } from '@/model/charactor';
import { del, get, patch, post, put } from './http';
import { v4 as uuidv4 } from 'uuid';

const transformDiagramData = (characterData: any) => {
  let nodeDatas: NodeType[] = [];
  let edgeDatas: EdgeType[] = [];
  let nodeUUID: string[] = [];

  characterData.Relations.array.forEach((ele: any) => {
    nodeUUID.push(ele.characterUUID);
  });

  characterData.Relations.forEach((dat: any) => {
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
      ...(dat.characterNode && {
        position: {
          x: dat.characterNode.x,
          y: dat.characterNode.y,
        },
      }),
    });

    if (dat.groupUUID) {
      edgeDatas.push({
        data: {
          source: dat.groupUUID,
          target: dat.characterUUID,
          type: 'group',
        },
      });
    }

    dat.relations.forEach((element: any) => {
      if (element.targetUUID && nodeUUID.includes(element.targetUUID)) {
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

  characterData.Groups.forEach((dat: any) => {
    nodeDatas.push({
      data: {
        id: dat.groupUUID,
        label: dat.groupName,
        type: 'group',
      },
      ...(dat.groupNode && {
        position: {
          x: dat.groupNode.x,
          y: dat.groupNode.y,
        },
      }),
    });
    if (dat.parentGroupUUID) {
      edgeDatas.push({
        data: {
          source: dat.parentGroupUUID,
          target: dat.groupUUID,
          type: 'group',
        },
      });
    }
  });

  characterData.Relations.forEach((dat: any) => {});

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
        if (newData !== null) {
          child.push(newData);
        } else {
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

export const getCharacterByName = async (workspace: string, name: string) => {
  if (name !== '') {
    const data = await get(
      `/character/search?workspaceUUID=${workspace}&characterName=${name}`,
    );
    return data;
  } else {
    return [];
  }
};

export const getCharacter = async (req: {
  workspace: string;
  uuid: string;
}) => {
  const data = await get(
    `/character?workspaceUUID=${req.workspace}&characterUUID=${req.uuid}`,
  );
  return data;
};

export const postCharacter = async (req: {
  workspace: string;
  group: string | null;
  name: string;
  uuid: string;
}) => {
  const data = await post(`/character`, {
    characterUUID: req.uuid,
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
  workspace: string;
}) => {
  const data = await patch(
    `/character?workspaceUUID=${req.workspace}&characterUUID=${req.params}`,
    req.body,
  );
  return data;
};

export const deleteCharacter = async (req: {
  workspace: string;
  uuid: string;
}) => {
  const data = await del(
    `/character?workspaceUUID=${req.workspace}&characterUUID=${req.uuid}`,
  );
  return data;
};

export const getRelationDiagramInformation = async (workspace: string) => {
  const characterData = await get(
    `/character/diagram?workspaceUUID=${workspace}`,
  );
  return transformDiagramData(characterData);
};

export const getCharacterDirectory = async (workspace: string) => {
  const data = await get(`/group/character/all?workspaceUUID=${workspace}`);
  const newData = transformCharacterDirectory(data);
  return newData;
};

export const patchCharacterName = async (req: {
  name: string;
  uuid: string;
  workspace: string;
}) => {
  const data = await get(
    `/character?workspaceUUID=${req.workspace}&characterUUID=${req.uuid}`,
  );
  const newData = { ...data, characterName: req.name };
  const response = await patch(
    `/character?workspaceUUID=${req.workspace}&characterUUID=${req.uuid}`,
    newData,
  );
  return response;
};

export const patchCharacterNodePosition = async (req: {
  workspaceUUID: string;
  characterUUID: string;
  x: number;
  y: number;
}) => {
  const data = await patch(
    `/character/node?workspaceUUID=${req.workspaceUUID}&characterUUID=${req.characterUUID}&x=${req.x}&y=${req.y}`,
  );
  return data;
};
