import { EdgeType, NodeType, characterType, poop } from '@/model/charactor';
import { del, get, patch, post, put } from './http';

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

export const postCharacter = async (workspace: string, group: string) => {
  const data = await post(`/character`, {
    workspaceUUID: workspace,
    groupUUID: group,
    characterName: '새 캐릭터',
    information: [{ '': '' }],
    relations: [{ content: '', targetName: '', targetUUID: null }],
  });
  return data;
};

export const patchCharacter = async (params: string, body: characterType) => {
  const data = await patch(`/character?characterUUID=${params}`, body);
  return data;
};

export const deleteCharacter = async (uuid: string) => {
  const data = await del(`/character?characterUUID=${uuid}`);
  return data;
};

export const getRelationDiagramInformation = async (workspace: string) => {
  const characterData = await get(`/character/diagram`);
  console.log(characterData);
  const groupData = await get(
    `/group/character/all?workspaceUUID=${workspace}`,
  );
  return transformDiagramData(characterData, groupData);
};
