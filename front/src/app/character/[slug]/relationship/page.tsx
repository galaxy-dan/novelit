'use client';

import React, { useEffect, useRef, useState } from 'react';
import { AiOutlineLoading3Quarters, AiOutlineCheck } from 'react-icons/ai';
import CytoscapeComponent from 'react-cytoscapejs';
import { Core, NodeSingular, EdgeSingular } from 'cytoscape';
import Image from 'next/image';
import { UseQueryResult, useMutation, useQuery } from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import {
  getRelationDiagramInformation,
  patchCharacterNodePosition,
} from '@/service/api/character';
import { patchGroupNodePosition } from '@/service/api/group';

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

type Props = {
  params: {
    slug: string;
  };
};

export default function RelationshipDiagram({ params }: Props) {
  var prevNode = { id: '', count: 0 };
  const router = useRouter();
  const [showGraph, setShowGraph] = useState<boolean>(false);
  const [renderGraph, setRenderGraph] = useState<boolean>(false);
  const { data: graphData }: UseQueryResult<graphType> = useQuery({
    queryKey: ['graph', params.slug],
    queryFn: () => getRelationDiagramInformation(params.slug),
    onError: () => {
      router.push(`/character/${params.slug}`);
    },
    staleTime: 0,
    refetchOnWindowFocus: true,
  });

  const moveCharacterMutate = useMutation({
    mutationFn: patchCharacterNodePosition,
  });

  const moveGroupMutate = useMutation({
    mutationFn: patchGroupNodePosition,
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
        'text-outline-width': '3px',
        color: 'white',
        fontSize: 22,
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
        if (ele.data().type === 'group') {
          moveGroupMutate.mutate({
            workspaceUUID: params.slug,
            groupUUID: ele.data().id,
            x: Math.round(ele.position('x')),
            y: Math.round(ele.position('y')),
          });
        } else {
          moveCharacterMutate.mutate({
            workspaceUUID: params.slug,
            characterUUID: ele.data().id,
            x: Math.round(ele.position('x')),
            y: Math.round(ele.position('y')),
          });
        }
      }
    });
    cy.resize();
  };

  const handleDragFree = (event: any, cy: Core) => {
    //여기서 position 변경해서 저장하기
    //이거도 저장만 하고 받을 필요는 없음
    event.target.position({
      x: Math.round(event.target.position('x')),
      y: Math.round(event.target.position('y')),
    });
    prevNode = { id: event.target.data().id, count: 0 };
    cy.$(':selected').unselect();
    cy.elements().style({ opacity: 1 });

    if (event.target.data().type === 'group') {
      moveGroupMutate.mutate({
        workspaceUUID: params.slug,
        groupUUID: event.target.data().id,
        x: event.target.position('x'),
        y: event.target.position('y'),
      });
    } else {
      moveCharacterMutate.mutate({
        workspaceUUID: params.slug,
        characterUUID: event.target.data().id,
        x: event.target.position('x'),
        y: event.target.position('y'),
      });
    }
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

  const [cy, setCy] = useState<Core>();

  useEffect(() => {
    if (cy) {
      cy.on('layoutstop', async () => {
        await setNodesPosition(cy);
        await setRenderGraph(true);
        await setShowGraph(true);
        cy.fit();
      });

      cy.on('select', 'node', (e) => handleNodeSelect(e, cy));

      cy.on('unselect', 'node', () => handleNodeUnselect(cy));
      cy.on('select', 'edge', (e) => handleEdgeSelect(e, cy));
      cy.on('unselect', 'edge', () => handleEdgeUnselect(cy));
      cy.on('free', 'node', (e) => handleNodeClicked(e, cy));
      cy.on('dragfreeon', 'node', (e) => handleDragFree(e, cy));
    }
  }, [cy]);

  useEffect(() => {
    if (cy) {
      let renderCount = 0;
      const fitGraph = () => {
        renderCount++;
        if (renderCount < 10) {
          cy.fit(cy.nodes(), 40);
        } else {
          cy.off('render', fitGraph);
        }
      };

      cy.on('render', fitGraph);
    }
  }, [cy]);

  return (
    <div className="select-none w-fit absolute left-[260px]  h-screen overflow-y-scroll scrollbar-hide">
      <div className="w-[60rem] mx-auto ml-10 pt-10">
        <div className="flex items-end justify-between">
          <div className="flex items-end">
            <p className="text-4xl font-extrabold mr-4">관계도</p>
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
            {
              <CytoscapeComponent
                elements={CytoscapeComponent.normalizeElements(
                  graphData || { nodes: [], edges: [] },
                )}
                zoomingEnabled={true}
                maxZoom={1.5}
                minZoom={0.3}
                autounselectify={false}
                boxSelectionEnabled={true}
                wheelSensitivity={0.1}
                layout={layout}
                stylesheet={styleSheet}
                className={`${!showGraph && 'invisible'}  w-full h-[80vh]`}
                cy={(cyInstance: Core) => {
                  setCy(cyInstance);
                }}
              />
            }
          </div>
        </div>
      </div>
    </div>
  );
}
