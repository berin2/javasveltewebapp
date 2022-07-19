
function range(size:number): number[]
{
    let ret:number[] = [];

    for(let i = 0; i < size; i++)
        ret.push(i);

    return ret;
}

export {range};