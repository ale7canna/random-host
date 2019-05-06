package ale7canna.randomhost.application

interface IStorage<TData> {
    fun store(data: TData)
    fun restoreLatest(): TData
}
