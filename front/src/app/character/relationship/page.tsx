'use client';

import React, { useState } from 'react';
import { AiOutlineLoading3Quarters, AiOutlineCheck } from 'react-icons/Ai';
import CytoscapeComponent from 'react-cytoscapejs';
import { Core, NodeSingular, EdgeSingular } from 'cytoscape';

export default function RelationshipDiagram() {
  const [width, setWith] = useState('100%');
  const [height, setHeight] = useState('400px');
  const [graphData, setGraphData] = useState({
    nodes: [
      { data: { id: '1', label: 'IP 1', type: 'ip' } },
      { data: { id: '2', label: 'Device 1', type: 'device' } },
      { data: { id: '3', label: 'IP 2', type: 'ip' } },
      { data: { id: '4', label: 'Device 2', type: 'device' } },
      { data: { id: '5', label: 'Device 3', type: 'device' } },
      { data: { id: '6', label: 'IP 3', type: 'ip' } },
      { data: { id: '7', label: 'Device 5', type: 'device' } },
      { data: { id: '8', label: 'Device 6', type: 'device' } },
      { data: { id: '9', label: 'Device 7', type: 'device' } },
      { data: { id: '10', label: 'Device 8', type: 'device' } },
      { data: { id: '11', label: 'Device 9', type: 'device' } },
      { data: { id: '12', label: 'IP 3', type: 'ip' } },
      { data: { id: '13', label: 'Device 10', type: 'device' } },
    ],
    edges: [
      {
        data: {
          source: '1', target: '2', label: 'Node2', type:'ip',
        },
      },
      {
        data: { source: '3', target: '4', label: 'Node4', type:'ip', },
      },
      {
        data: { source: '4', target: '5', label: 'Node111', type:'device', },
      },
      {
        data: { source: '3', target: '5', label: 'Node5', type:'ip', },
      },
      {
        data: { source: '6', target: '5', label: ' 6 -> 5', type:'ip', },
      },
      {
        data: { source: '4', target: '10', label: ' 2 -> 8' , type:'device',},
      },
      {
        data: { source: '10', target: '4', label: ' 8 -> 2' , type:'device',},
      },
      {
        data: { source: '6', target: '7', label: ' 6 -> 7', type:'ip', },
      },
      {
        data: { source: '6', target: '8', label: ' 6 -> 8', type:'ip', },
      },
      {
        data: { source: '6', target: '9', label: ' 6 -> 9' , type:'ip',},
      },
      {
        data: { source: '3', target: '13', label: ' 3 -> 13', type:'ip', },
      },
      {
        data: { source: '3', target: '6', label: ' 3 -> 6', type:'ip', },
      },
      {
        data: { source: '9', target: '11', label: ' 3 -> 6', type:'device', },
      },
    ],
  });

  const layout = {
    name: 'breadthfirst',
    fit: true,
    // circle: true,
    directed: true,
    padding: 50,
    // spacingFactor: 1.5,
    animate: true,
    animationDuration: 1000,
    avoidOverlap: true,
    nodeDimensionsIncludeLabels: false,
  };

  const styleSheet = [
    {
      selector: 'node',
      style: {
        backgroundColor: '#4a56a6',
        width: 30,
        height: 30,
        label: 'data(label)',
        'overlay-padding': '6px',
        'z-index': 10,
        'text-outline-color': '#4a56a6',
        'text-outline-width': '2px',
        color: 'white',
        fontSize: 20,
      },
    },
    {
      selector: 'node:selected',
      style: {
        'border-width': '6px',
        'border-color': '#AAD8FF',
        'border-opacity': 0.5,
        'background-color': '#77828C',
        width: 50,
        height: 50,
        //text props
        'text-outline-color': '#77828C',
        'text-outline-width': 8,
      },
    },
    {
      selector: "node[type='device']",
      style: {},
    },
    {
      selector: 'edge',
      style: {
        width: 3,
        // "line-color": "#6774cb",
        'line-color': '#AAD8FF',
        'target-arrow-color': '#6774cb',
        'curve-style': 'bezier',
        label: 'data(label)',
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
      visitedEdges.add(edge.id());
      const connectedNode = edge
        .connectedNodes()
        .filter((connectedNode) => !visitedNodes.has(connectedNode.id()));
      if (
        connectedNode.length > 0 &&
        (node.data().type !== 'device' ||
          connectedNode.data().type !== 'device')
      ) {
        dfs(connectedNode[0], visitedNodes, visitedEdges);
      }
    });
  };

  const handleNodeSelect = (event: any, cy: Core) => {
    const selectedNode: NodeSingular = event.target;
    const type = selectedNode.data().type;
    if (type === 'ip') {
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
      unvisitedElements.style({ opacity: 0.5 });
    } else if (type === 'device') {
      const connectedNodes = selectedNode.edgesTo('*').connectedNodes();
      if (connectedNodes.length === 0) {
        cy.elements().not(selectedNode).style({ opacity: 0.5 });
      } else {
        const visitedNodes = new Set<string>(
          connectedNodes.map((node) => node.id()),
        );
        const visitedEdges = new Set<string>(
          connectedNodes.edgesTo('*').map((edge) => edge.id()),
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
        unvisitedElements.style({ opacity: 0.5 });
      }
    }
  };

  const handleNodeUnselect = (cy: Core) => {
    cy.elements().style({ opacity: 1 });
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
        <h1>Cytoscape example</h1>
        <div
          style={{
            border: '1px solid',
            backgroundColor: '#f5f6fe',
          }}
        >
          <CytoscapeComponent
            elements={CytoscapeComponent.normalizeElements(graphData)}
            // pan={{ x: 200, y: 200 }}
            style={{ width: width, height: height }}
            zoomingEnabled={true}
            maxZoom={3}
            minZoom={0.1}
            autounselectify={false}
            boxSelectionEnabled={true}
            layout={layout}
            stylesheet={styleSheet}
            cy={(cy) => {
              cy.on('select', 'node', (e) => handleNodeSelect(e, cy));
              cy.on('unselect', 'node', () => handleNodeUnselect(cy));

              cy.style().selector('node[type = "ip"]').style({
                'background-color': '#6fb1fc'
              }).selector('node[type = "device"]').style({
                'background-color': '#eda1ed'
              }).selector('edge[type = "ip"]').style({
                'line-color': '#d6d627'
              }).selector('edge[type = "device"]').style({
                'line-color': '#f5a45d'
              }).update();

              cy.style().selector('edge').style({
                'curve-style': 'bezier',
                'control-point-step-size': 80 // 곡선의 곡률을 조절
              }).update();
            }}
          />
        </div>
      </div>
    </div>
  );
}
