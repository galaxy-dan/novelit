'use client';

import React, { useState } from 'react';
import { AiOutlineLoading3Quarters, AiOutlineCheck } from 'react-icons/Ai';
import CytoscapeComponent from 'react-cytoscapejs';
import { Core, NodeSingular, EdgeSingular } from 'cytoscape';
import Image from 'next/image';
import loadingImg from 'frontpublicimageloadingImg.gif';

export default function RelationshipDiagram() {
  const [showGraph, setShowGraph] = useState(false);
  const [count, setCount] = useState(0);
  const [width, setWith] = useState('100%');
  const [height, setHeight] = useState('450px');
  const [graphData, setGraphData] = useState({
    nodes: [
      {
        data: { id: '1', label: '그룹 1', type: 'group' },
        position: { x: 200, y: 200 },
        style: {},
      },
      {
        data: { id: '2', label: '캐릭터 1', type: 'character' },
        position: { x: 200, y: 200 },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '3', label: '그룹 2', type: 'group' },
        position: { x: 200, y: 200 },
        style: {},
      },
      {
        data: { id: '4', label: '캐릭터 2', type: 'character' },
        position: { x: 200, y: 200 },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '5', label: '캐릭터 3', type: 'character' },
        position: { x: 200, y: 200 },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '6', label: '그룹 3', type: 'group' },
        position: { x: 200, y: 200 },
        style: {},
      },
      {
        data: { id: '7', label: '캐릭터 5', type: 'character' },
        position: { x: 200, y: 200 },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '8', label: '캐릭터 6', type: 'character' },
        position: { x: 200, y: 200 },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '9', label: '캐릭터 7', type: 'character' },
        position: { x: 200, y: 200 },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
          backgroundFit: 'cover cover',
        },
      },
      {
        data: { id: '10', label: '캐릭터 8', type: 'character' },
        position: { x: 200, y: 200 },
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
        data: { id: '12', label: '그룹 3', type: 'group' },
        style: {},
      },
      {
        data: { id: '13', label: '캐릭터 10', type: 'character' },
        position: { x: 200, y: 200 },
        style: {
          backgroundImage:
            'https://images.unsplash.com/photo-1697541283989-bbefb5982de9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8',
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
    } else if (type === 'character') {
      const connectedNodes = selectedNode.edgesTo('*').connectedNodes();

      if (connectedNodes.length === 0) {
        cy.elements().not(selectedNode).style({ opacity: 0.2 });
      } else {
        const visitedNodes = new Set<string>(
          connectedNodes.map((node) => node.id()),
        );
        const visitedEdges = new Set<string>(
          selectedNode.edgesTo(connectedNodes).map((edge) => edge.id()),
        );
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

  const handleNodeUnselect = (cy: Core) => {
    cy.elements().style({ opacity: 1 });
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

  return (
    <div className="mx-60 mt-20">
      <div className="flex items-end justify-between">
        <div className="flex items-end">
          <p className="text-4xl font-bold mr-4">관계도</p>
        </div>
        <div className="flex items-center">
          <p className="text-2xl font-bold mr-2">저장중</p>
          <AiOutlineLoading3Quarters className="animate-spin text-xl " />
          <AiOutlineCheck className="text-2xl" />
        </div>
      </div>

      <div>
        <div className="rounded-xl border border-gray-300 shadow-md mt-12 " style={{ width: width, height: height }}>
          <div className={`h-full w-full relative ${showGraph && 'hidden'}` }>
            <Image
              src="/image/loadingImg.gif"
              alt="로딩이미지"
              width={1000}
              height={1000}
              className={`h-52 w-52 absolute mx-auto my-auto left-0 right-0`}
            />
          </div>
          <CytoscapeComponent
            elements={CytoscapeComponent.normalizeElements(graphData)}
            style={{ width: width, height: height }}
            zoomingEnabled={true}
            maxZoom={1.5}
            minZoom={0.3}
            autounselectify={false}
            boxSelectionEnabled={true}
            layout={layout}
            stylesheet={styleSheet}
            className={`${!showGraph && 'invisible'}`}
            cy={(cy) => {
              cy.on('layoutstop', () => {
                setNodesPosition(cy);
                setShowGraph(true);
              });

              cy.on('select', 'node', (e) => handleNodeSelect(e, cy));
              cy.on('unselect', 'node', () => handleNodeUnselect(cy));
              cy.on('select', 'edge', (e) => handleEdgeSelect(e, cy));
              cy.on('unselect', 'edge', () => handleEdgeUnselect(cy));

              cy.on('dragfree', 'node', (e) => {
                //여기서 position 변경해서 저장하기
                // 이거도 저장만 하고 받을 필요는 없음
                console.log(
                  e.target.id() +
                    ': { ' +
                    e.target.position('x') +
                    ',' +
                    e.target.position('y') +
                    ' }',
                );
              });
            }}
          />
        </div>
      </div>
    </div>
  );
}
