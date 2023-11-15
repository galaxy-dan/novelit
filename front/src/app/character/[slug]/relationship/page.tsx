'use client';

import React, { useState } from 'react';
import { AiOutlineLoading3Quarters, AiOutlineCheck } from 'react-icons/ai';
import CytoscapeComponent from 'react-cytoscapejs';
import { Core, NodeSingular, EdgeSingular } from 'cytoscape';
import Image from 'next/image';
type NodeType = {
  data: {
    id: string;
    label?: string;
    type?: string;
    // 추가적으로 필요한 필드들
  };
  position?: {
    x: number;
    y: number;
  };
  style: {
    backgroundImage?: string;
    backgroundFit?: string;
  };
};
type EdgeType = {
  data: {
    id?: string;
    source: string;
    target: string;
    type?: string;
    label?: string;
    // 추가적으로 필요한 필드들
  };
};

type graphType = {
  nodes: NodeType[];
  edges: EdgeType[];
};
export default function RelationshipDiagram() {
  var prevNode = { id: '', count: 0 };

  const [showGraph, setShowGraph] = useState<boolean>(false);
  const [graphData, setGraphData] = useState<graphType>({
    nodes: [
      {
        data: { id: '1', label: 'SSAFY 대전', type: 'group' },
        style: {},
      },
      {
        data: { id: '2', label: '캐릭터 1', type: 'character' },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '3', label: 'SSAFY 서울', type: 'group' },
        style: {},
      },
      {
        data: { id: '4', label: '캐릭터 2', type: 'character' },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '5', label: '캐릭터 3', type: 'character' },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1580164631075-b3f1304f4051?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OTV8fG1hcnZlbHxlbnwwfHwwfHx8MA%3D%3D',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '6', label: '1반', type: 'group' },
        style: {},
      },
      {
        data: { id: '7', label: '캐릭터 5', type: 'character' },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1636840438199-9125cd03c3b0?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nzl8fG1hcnZlbHxlbnwwfHwwfHx8MA%3D%3D',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '8', label: '캐릭터 6', type: 'character' },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1608889175250-c3b0c1667d3a?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NTB8fG1hcnZlbHxlbnwwfHwwfHx8MA%3D%3D',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '9', label: '캐릭터 7', type: 'character' },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1531259683007-016a7b628fc3?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjZ8fG1hcnZlbHxlbnwwfHwwfHx8MA%3D%3D',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '10', label: '캐릭터 8', type: 'character' },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '11', label: '캐릭터 9', type: 'character' },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '12', label: 'SSAFY 부울경', type: 'group' },
        style: {},
      },
      {
        data: { id: '13', label: '캐릭터 10', type: 'character' },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1596727147705-61a532a659bd?auto=format&fit=crop&q=80&w=1887&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '14', label: '2반', type: 'group' },
        style: {},
      },
      {
        data: { id: '15', label: '캐릭터 11', type: 'character' },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1635805737707-575885ab0820?auto=format&fit=crop&q=80&w=1887&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '16', label: '캐릭터 12', type: 'character' },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1573140247632-f8fd74997d5c?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          backgroundFit: 'cover cover',
        },
      },
    ],
    edges: [
      {
        data: {
          source: '1',
          target: '2',
          type: 'group',
          label: '',
        },
      },
      {
        data: { source: '3', target: '4', label: '', type: 'group' },
      },
      {
        data: { source: '4', target: '5', label: '증오', type: 'character' },
      },
      {
        data: { source: '3', target: '5', label: '', type: 'group' },
      },
      {
        data: { source: '4', target: '10', label: ' 사랑', type: 'character' },
      },
      {
        data: { source: '10', target: '4', label: ' 질투', type: 'character' },
      },
      {
        data: { source: '6', target: '7', label: '', type: 'group' },
      },
      {
        data: { source: '6', target: '8', label: '', type: 'group' },
      },
      {
        data: { source: '6', target: '9', label: '', type: 'group' },
      },
      {
        data: { source: '3', target: '13', label: '', type: 'group' },
      },
      {
        data: { source: '3', target: '6', label: '', type: 'group' },
      },
      {
        data: { source: '9', target: '11', label: ' 혐오', type: 'character' },
      },
      {
        data: {
          source: '14',
          target: '15',
          type: 'group',
          label: '',
        },
      },
      {
        data: { source: '14', target: '16', label: '', type: 'group' },
      },
      {
        data: { source: '15', target: '16', label: '경쟁', type: 'character' },
      },
      {
        data: {
          source: '5',
          target: '11',
          type: 'character',
          label: '동료',
        },
      },
      {
        data: { source: '2', target: '13', label: '우정', type: 'character' },
      },
      {
        data: { source: '7', target: '15', label: '파트너', type: 'character' },
      },
      {
        data: { source: '8', target: '16', label: '경쟁', type: 'character' },
      },
      {
        data: {
          source: '9',
          target: '10',
          type: 'character',
          label: '협력',
        },
      },
      {
        data: { source: '3', target: '14', label: '', type: 'group' },
      },
      {
        data: {
          source: '13',
          target: '15',
          label: '상호 존경',
          type: 'character',
        },
      },
    ],
  });

  const layout = {
    name: 'breadthfirst',
    fit: true,
    directed: true,
    padding: 50,
    animate: true,
    animationDuration: 0,
    avoidOverlap: true,
    nodeDimensionsIncludeLabels: false,
  };

  // 원래의 위치 정보를 저장할 변수를 선언합니다.
  let originalPositions: any = [];

  // graphData.nodes[i].position의 정보를 복사하여 저장합니다.
  if (graphData && graphData.nodes) {
    originalPositions = graphData.nodes.map((node) => {
      return node.position ? { ...node.position } : null;
    });
  }

  const styleSheet = [
    {
      selector: 'node',
      style: {
        width: 30,
        height: 30,
        label: 'data(label)',
        'overlay-padding': '6px',
        'text-outline-color': '#4a56a6',
        'text-outline-width': '2px',
        color: 'white',
        fontSize: 20,
        'background-color': '#d6d627',
      },
    },
    {
      selector: "node[type='character']",
      style: {
        'background-color': '#6fb1fc',
        'border-width': '6px',
        'border-color': '#6fb1fc',
        'border-opacity': 0.5,
        width: 80,
        height: 80,
      },
    },
    {
      selector: "node[type='group']",
      style: {
        'background-color': '#d6d627',
        'border-width': '6px',
        'border-color': '#d6d627',
        'border-opacity': 0.5,
        width: 40,
        height: 40,
        textValign: 'center',
        textHalign: 'center',
        'text-outline-color': '#555',
        'text-outline-width': '4px',
        color: '#fff',
        fontSize: 28,
      },
    },
    {
      selector: 'node[type="character"]:selected',
      style: {
        'border-width': '6px',
        'border-color': '#aa3333',
        'border-opacity': 0.5,
        width: 100,
        height: 100,
        //text props
        'text-outline-color': '#aa3333',
        'text-outline-width': 4,
      },
    },
    {
      selector: 'edge',
      style: {
        width: 7,
        'line-color': '#AAD8FF',
        'target-arrow-color': '#6774cb',
        'curve-style': 'bezier',
        label: 'data(label)',
        'control-point-step-size': 80,
        fontSize: 22,
        'text-outline-color': '#6774cb',
        'text-outline-width': '3px',
        color: '#fff',
      },
    },
    {
      selector: 'edge[type="group"]',
      style: {
        'line-color': '#d6d627',
      },
    },
    {
      selector: 'edge[type="character"]',
      style: {
        'line-color': '#6fb1fc',
        'target-arrow-shape': 'triangle',
      },
    },
  ];

  const dfs = (
    node: NodeSingular,
    visitedNodes: Set<string>,
    visitedEdges: Set<string>,
  ) => {
    visitedNodes.add(node.id());
    const connectedEdges = node.edgesTo('*');
    connectedEdges.forEach((edge: EdgeSingular) => {
      const connectedNode = edge
        .connectedNodes()
        .filter((connectedNode) => !visitedNodes.has(connectedNode.id()));
      if (
        connectedNode.length > 0 &&
        (node.data().type !== 'character' ||
          connectedNode.data().type !== 'character')
      ) {
        visitedEdges.add(edge.id());
        dfs(connectedNode[0], visitedNodes, visitedEdges);
      }
    });
  };

  const handleNodeSelect = (event: any, cy: Core) => {
    const selectedNode: NodeSingular = event.target;
    const type = selectedNode.data().type;
    if (type === 'group') {
      const visitedNodes = new Set<string>();
      const visitedEdges = new Set<string>();
      dfs(selectedNode, visitedNodes, visitedEdges);
      const unvisitedElements = cy
        .elements()
        .difference(
          cy.$(
            [
              ...Array.from(visitedNodes).map((id) => `node[id = '${id}']`),
              ...Array.from(visitedEdges).map((id) => `edge[id = '${id}']`),
            ].join(', '),
          ),
        );
      unvisitedElements.style({ opacity: 0.2 });
    }
  };

  const handleNodeUnselect = (cy: Core) => {
    cy.elements().style({ opacity: 1 });
    prevNode = { id: '', count: 0 };
  };

  const handleEdgeSelect = (event: any, cy: Core) => {
    const selectedEdge: EdgeSingular = event.target;
    const source = selectedEdge.source();
    const target = selectedEdge.target();

    const unvisitedElements = cy.elements().filter((ele) => {
      const isSourceOrTarget =
        ele.id() === source.id() || ele.id() === target.id();
      const isSelectedEdge = ele.id() === selectedEdge.id();
      return !(isSourceOrTarget || isSelectedEdge);
    });

    unvisitedElements.style({ opacity: 0.2 });
  };

  const handleEdgeUnselect = (cy: Core) => {
    cy.elements().style({ opacity: 1 });
  };

  const setNodesPosition = (cy: Core) => {
    cy.nodes().map((ele, i) => {
      if (originalPositions[i] !== null) {
        ele.position({
          x: originalPositions[i].x || 0,
          y: originalPositions[i].y || 0,
        });
      } else {
        // 여기서 position 저장안 된 놈 저장하기
        // 이건 굳이 새로 받을 필요없음
      }
    });
  };

  const handleDragFree = (event: any, cy: Core) => {
    //여기서 position 변경해서 저장하기
    //이거도 저장만 하고 받을 필요는 없음
    console.log(prevNode);

    prevNode = { id: event.target.data().id, count: 0 };
    cy.$(':selected').unselect();
    cy.elements().style({ opacity: 1 });
  };

  const handleNodeClicked = (event: any, cy: Core) => {
    const selectedNode: NodeSingular = event.target;
    const type = selectedNode.data().type;

    if (type === 'character') {
      cy.elements().style({ opacity: 1 });

      var selectedNodes = cy.$(':selected');
      var nodesToUnselect = selectedNodes.difference(event.target);
      nodesToUnselect.unselect();
      event.target.select();

      // 1: 모두 2: 받은 것 3: 주는 것
      if (prevNode.id === selectedNode.data().id) {
        prevNode = {
          id: selectedNode.data().id,
          count: (prevNode.count % 3) + 1,
        };
      } else {
        prevNode = { id: selectedNode.data().id, count: 1 };
      }

      var connectedNodes: cytoscape.NodeCollection = cy.collection();

      if (prevNode.count === 1) {
        connectedNodes = selectedNode.edgesWith('*').connectedNodes();
      } else if (prevNode.count === 2) {
        connectedNodes = selectedNode.edgesTo('*').connectedNodes();
      } else if (prevNode.count === 3) {
        connectedNodes = selectedNode
          .incomers()
          .sources()
          .filter((em) => em.data().type == 'character');
        connectedNodes.merge(selectedNode);
      }

      // 연결된 노드가 없으면
      if (connectedNodes.length === 0) {
        cy.elements().not(selectedNode).style({ opacity: 0.2 });
      }
      // 연결된 노드가 있으면
      else {
        const visitedNodes = new Set<string>(
          connectedNodes.map((node) => node.id()),
        );

        var visitedEdges = new Set<string>();

        if (prevNode.count === 1) {
          visitedEdges = new Set<string>(
            selectedNode.edgesWith(connectedNodes).map((edge) => edge.id()),
          );
        } else if (prevNode.count === 2) {
          visitedEdges = new Set<string>(
            selectedNode.edgesTo(connectedNodes).map((edge) => edge.id()),
          );
        } else if (prevNode.count === 3) {
          visitedEdges = new Set<string>(
            connectedNodes.edgesTo(selectedNode).map((edge) => edge.id()),
          );
        }

        const unvisitedElements = cy
          .elements()
          .difference(
            cy.$(
              [
                ...Array.from(visitedNodes).map((id) => `node[id = '${id}']`),
                ...Array.from(visitedEdges).map((id) => `edge[id = '${id}']`),
              ].join(', '),
            ),
          );
        unvisitedElements.style({ opacity: 0.2 });
      }
    }
  };

  return (
    <div className="mx-80 my-20">
      <div className="flex items-end justify-between">
        <div className="flex items-end">
          <p className="text-4xl font-extrabold mr-4">관계도</p>
        </div>
        <div className="flex items-center">
          <p className="text-2xl font-extrabold mr-2">저장중</p>
          <AiOutlineLoading3Quarters className="animate-spin text-xl " />
          <AiOutlineCheck className="text-2xl" />
        </div>
      </div>

      <div>
        <div
          className={`rounded-xl border border-gray-300 shadow-md mt-12 w-full h-[80vh]`}
        >
          <div className={`h-full w-full relative ${showGraph && 'hidden'}`}>
            <Image
              src="/images/loadingImg.gif"
              alt="로딩이미지"
              width={1000}
              height={1000}
              className={`h-[30vh] w-[30vh] absolute mx-auto my-auto top-0 left-0 right-0 bottom-0`}
              loading="eager"
              priority={true}
            />
          </div>
          <CytoscapeComponent
            elements={CytoscapeComponent.normalizeElements(graphData)}
            zoomingEnabled={true}
            maxZoom={1.5}
            minZoom={0.3}
            autounselectify={false}
            boxSelectionEnabled={true}
            wheelSensitivity={0.1}
            layout={layout}
            stylesheet={styleSheet}
            className={`${!showGraph && 'invisible'}  w-full h-[80vh]`}
            cy={(cy: Core) => {
              cy.on('layoutstop', () => {
                setNodesPosition(cy);
                setShowGraph(true);
              });

              cy.on('select', 'node', (e) => handleNodeSelect(e, cy));
              cy.on('unselect', 'node', () => handleNodeUnselect(cy));
              cy.on('select', 'edge', (e) => handleEdgeSelect(e, cy));
              cy.on('unselect', 'edge', () => handleEdgeUnselect(cy));
              cy.on('', '', () => {});
              cy.on('free', 'node', (e) => handleNodeClicked(e, cy));

              cy.on('dragfreeon', 'node', (e) => handleDragFree(e, cy));
            }}
          />
        </div>
      </div>
    </div>
  );
}
