import {throwError} from "svelte-preprocess/dist/modules/errors";

class Graph<ViewEnumType>
{
    public graph: Array<Array<Number>>;
    public currentVertex:number;
    constructor(maximum: number)
    {
        let size = maximum + 1;
        this.graph = new Array(size);
        this.graph.fill(new Array(size).fill(0,0,size),0,size);
    }

    public createEdge(currentVertex:number, toVertex:number):void
    {
        if(currentVertex < this.graph.length && toVertex <  this.graph.length)
            this.graph[currentVertex][toVertex] = 1;
        else
            throwError(`Graph vertex index out of bounds.Maximum vertex index is ${this.graph.length}. Supplied vertices to function are ${currentVertex} and ${toVertex}`);
    }
    public createBiDirectionalEdge(currentVertex:number, toVertex:number):void
    {
        this.createEdge(currentVertex,toVertex);
        this.createEdge(toVertex,currentVertex);
    }

    public canTransition(toVertex:number):boolean
    {
        return this.graph[this.currentVertex][toVertex] == 1;
    }

    public transition(toVertex:number): Graph<ViewEnumType>
    {
        if(this.canTransition(toVertex))
            this.currentVertex = toVertex;
        else
            throwError(`Invalid edge transition from vertex ${this.currentVertex} to ${toVertex} requested, but this does not define an edge on the current graph.`);

        return this;
    }
}

export default Graph;