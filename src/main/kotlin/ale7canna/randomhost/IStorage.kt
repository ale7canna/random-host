package ale7canna.randomhost

interface IStorage<TData> {
    fun store(data: TData)
    fun restoreLatest(): TData
}
