package be.kdg.programming5.musicwebsite.util.converter;

public interface ViewModelConverter<ViewClass, ModelClass> {
    ModelClass convertToModel(ViewClass viewClass);
    ViewClass convertToView(ModelClass modelClass);
}
