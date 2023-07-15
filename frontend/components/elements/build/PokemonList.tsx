import {
    DataGrid,
    GridColDef,
    GridRowsProp,
    GridToolbarColumnsButton,
    GridToolbarContainer, GridToolbarExport, GridToolbarFilterButton
} from "@mui/x-data-grid";

const rows: GridRowsProp = [
  { id: 1, name: 'ジラーチ', type: 'はがね', role: 'a', nature: 'いじっぱり', ability: 'てんのめぐみ', good: 'いのちのたま', ev: '252,252,4,0,0,0' },
  { id: 2, name: 'ポッチャマ', type: 'はがね', role: 'b', nature: 'ひかえめ', ability: 'げきりゅう', good: 'いのちのたま', ev: '252,252,4,0,0,0' },
  { id: 3, name: 'ポットデス', type: 'ゴースト', role: 'c', nature: 'ひかえめ', ability: 'のろわれボディ', good: 'いのちのたま', ev: '252,252,4,0,0,0' },
]

const CustomToolbar = () => {
    return (
        <GridToolbarContainer>
            <GridToolbarColumnsButton />
            <GridToolbarFilterButton/>
            <GridToolbarExport />
        </GridToolbarContainer>
    )
}

const PokemonList= () => {
    const cols: GridColDef[] = [
    {
      field: 'name',
      headerName: '名前'
    },
    {
      field: 'type',
      headerName: 'テラスタイプ',
        editable: true
    },
    {
      field: 'role',
      headerName: '役割'
    },
    {
      field: 'nature',
      headerName: '性格'
    },
    {
      field: 'ability',
      headerName: '特性'
    },
    {
      field: 'good',
      headerName: '道具'
    },
    {
      field: 'ev',
      headerName: '努力値'
    }
  ]

  return (
    <div style={{ width: '100%' }}>
      <DataGrid
        columns={cols}
        rows={rows}
        density="comfortable"
        autoHeight
        slots={{
          toolbar: CustomToolbar,　　// ツールバーを指定する
        }}
        pageSizeOptions={[6, 12]}
      />
    </div>)
}

export default PokemonList